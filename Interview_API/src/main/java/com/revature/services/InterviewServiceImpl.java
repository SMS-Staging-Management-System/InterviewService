package com.revature.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.revature.cognito.constants.CognitoRoles;
import com.revature.cognito.utils.CognitoUtil;
import com.revature.dtos.AssociateInterview;
import com.revature.dtos.EmailList;
import com.revature.dtos.FeedbackData;
import com.revature.dtos.FeedbackStat;
import com.revature.dtos.Interview24Hour;
import com.revature.dtos.InterviewAssociateJobData;
import com.revature.dtos.NewAssociateInput;
import com.revature.dtos.NewInterviewData;
import com.revature.dtos.NumberOfInterviewsCount;
import com.revature.dtos.UserDto;
import com.revature.exceptions.ResourceNotFoundException;
import com.revature.feign.IUserClient;
import com.revature.models.AssociateInput;
import com.revature.models.Client;
import com.revature.models.FeedbackStatus;
import com.revature.models.Interview;
import com.revature.models.InterviewFeedback;
import com.revature.models.InterviewFormat;
import com.revature.models.User;
import com.revature.repos.AssociateInputRepo;
import com.revature.repos.ClientRepo;
import com.revature.repos.FeedbackRepo;
import com.revature.repos.FeedbackStatusRepo;
import com.revature.repos.InterviewFormatRepo;
import com.revature.repos.InterviewRepo;
import com.revature.utils.ListToPage;

@Service
public class InterviewServiceImpl implements InterviewService {

	@Autowired
	private InterviewRepo interviewRepo;

	@Autowired
	private AssociateInputRepo associateRepo;
	
	@Autowired
	private ClientRepo clientRepo;

	@Autowired
	private IUserClient userClient;

	@Autowired
	private CognitoUtil cognitoUtil;

	@Autowired
	private FeedbackRepo feedbackRepo;
	
	public Interview save(Interview i) {
		return interviewRepo.save(i);
	}
	
	@Autowired
	private FeedbackStatusRepo feedbackStatusRepo ;

	@Autowired
	private InterviewFormatRepo interviewFormatRepo;
	@Override
	public Interview update(Interview i) {
		return interviewRepo.save(i);
	}

	public Interview delete(Interview i) {
		return null;
	}

	@Override
	public List<Interview> findAll() {
		List<String> roles = cognitoUtil.getRequesterRoles();
		if(roles.contains(CognitoRoles.ADMIN) || roles.contains(CognitoRoles.STAGING_MANAGER))
			return interviewRepo.findAll();
		else {
			String email = cognitoUtil.getRequesterClaims().getEmail();
			return interviewRepo.findByAssociateEmail(email);
		}
	}
	
	@Override
	public List<Interview> findAllTest() {
		return interviewRepo.findAll();
	}


	public Interview addNewInterview(NewInterviewData i) {
		
		String managerEmail = i.getManagerEmail(); 
		if(managerEmail == "") {
			managerEmail = cognitoUtil.getRequesterClaims().getEmail();
		}
		
		String associateEmail = i.getAssociateEmail();
		Date scheduled = new Date(i.getDate());// TODO: check this is valid date
		String location = i.getLocation();
		String client = i.getClient();
				
		Client c = clientRepo.getByClientName(client);
				
		if (c == null) {
			c = new Client(0, client);
			clientRepo.save(c);
		}
				
		Interview newInterview = new Interview(0, managerEmail, associateEmail, scheduled, null, null, location, null, null, c);	
		
		return save(newInterview);
	}


	public Page<Interview> findAll(Pageable page) {
		return interviewRepo.findAll(page);
	}
	
	//MINE
	public Page<Interview> findAllEd(String search, Pageable pageable) {

		InterviewSpecificationsBuilder builder = new InterviewSpecificationsBuilder();
		
		//This regular expression matches the pattern below. In the search string this looks like key:value
		//Where key and value are non white space characters (\\S) and not * (&&[^*])
		Pattern pattern = Pattern.compile("([\\S&&[^*]]+?)(:|<|>)([\\S&&[^*]]+?),");
		Matcher matcher = pattern.matcher(search.replace("*,", "*").replace(" ", "_") + ",");
		while (matcher.find()) {
			System.out.println(matcher.group(3));
			builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
		}

		Specification<Interview> spec = builder.build();

		return interviewRepo.findAll(spec, pageable);
	}
	
	
	public Page<Interview> findAllByAssociateEmail(String search, Pageable page) {
		
		InterviewSpecificationsBuilder builder = new InterviewSpecificationsBuilder();
		
		//This regular expression matches the pattern below. In the search string this looks like key:value
		//Where key and value are non white space characters (\\S) and not * (&&[^*])
		Pattern pattern = Pattern.compile("([\\S&&[^*]]+?)(:|<|>)([\\S&&[^*]]+?),");
		Matcher matcher = pattern.matcher(search.replace("*,", "*").replace(" ", "_") + ",");
		while (matcher.find()) {
			System.out.println(matcher.group(3));
			builder.with(matcher.group(1), matcher.group(2), matcher.group(3));
		}

		Specification<Interview> spec = builder.build();
		
		
		return interviewRepo.findAll(spec, page);
	}
	
	
	
	@Override
	public Page<Interview> findAll(Specification<Interview> spec, Pageable pageable) {
		// TODO Auto-generated method stub
		return interviewRepo.findAll(spec, pageable);
	}
	
	@Override
	public List<Interview> getInterviewsStaging() {
		// TODO Auto-generated method stub
		List<Interview> stagingInterviews = interviewRepo.findAll().stream().filter((item) -> {
        	String assocEmail = item.getAssociateEmail();        	
        	
        	User user = null;
    		try {
    		user = userClient.findByEmail(assocEmail).getBody();
    		} catch(Exception e) {
    			e.printStackTrace();		
    		}

        	if(user != null) {
        		System.out.println(user.getUserStatus().getSpecificStatus());
        			if (user.getUserStatus().getSpecificStatus().equals("Staging")) {
        				return true;
        			}
        	}
        	return false;
        }).collect(Collectors.toList());
		
		return stagingInterviews;
	}

	public List<AssociateInterview> findInterviewsPerAssociate() {
		List<Interview> interviews = interviewRepo.findAll();
		List<AssociateInterview> associates = new ArrayList<AssociateInterview>();

		for (Interview I : interviews) {
			AssociateInterview A = new AssociateInterview(I);
			int index = associates.indexOf(A);
			System.out.println("New: " + A);
			if (index >= 0) {
				A = associates.get(index);
				A.incrementInterviewCount();
				associates.set(index, A);
				System.out.println("Incremented: " + A);
			} else {
				associates.add(A);
			}
		}
		System.out.println("List created");
		for(AssociateInterview A: associates) {
			try{
				User U = userClient.findByEmail(A.getAssociateEmail()).getBody();
				String Name = U.getFirstName();
				if(Name=="") {
					Name=U.getLastName();
				} else {
					Name+=" "+ U.getLastName();
				}
				System.out.println(Name);
				A.setAssociateName(Name);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		Collections.sort(associates);
		return associates;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public Page<AssociateInterview> findInterviewsPerAssociate(Pageable page) {
		PageImpl PI = ListToPage.getPage(findInterviewsPerAssociate(), page);
		return PI;
	}
	
	@Override
	public NumberOfInterviewsCount findAssociateInterviewsData() {
		List<AssociateInterview> interviewsPerAssociate = findInterviewsPerAssociate();
		HashMap<String, Integer> maxHolder = new HashMap<>();
		maxHolder.put("max", 0);
		interviewsPerAssociate.forEach(stat -> {
			if(stat.getInterviewCount() > maxHolder.get("max")) {
				maxHolder.put("max", stat.getInterviewCount());
			}
		});
		int[] counts = new int[maxHolder.get("max").intValue() + 1];
		interviewsPerAssociate.forEach(stat -> {
			int index = stat.getInterviewCount();
			counts[index]++;
		});
		return new NumberOfInterviewsCount(interviewsPerAssociate.size(), counts);
	}
	
	@Override
	public UserDto findByEmail(String email) {
		User U = (User)userClient.findByEmail(email).getBody();
		UserDto user = new UserDto();
		user.setFirstName(U.getFirstName());
		user.setLastName(U.getLastName());
		user.setEmail(U.getEmail());
		user.setPhoneNumber(U.getPhoneNumber());
		user.setUserId(U.getUserId());
		return user;
	}
	
	//Method for the Dashboard depends in the method on top to get all the interview count
	//this method returns only associates with 5 interviews or more using streams.
	@Override
	public List<AssociateInterview> getAssociatesWithFiveOrMore() {
		List<AssociateInterview> associates = findInterviewsPerAssociate();
		
		return associates.stream()
				.filter((a)->a.getInterviewCount()>4)
				.collect(Collectors.toList());
	}
	@SuppressWarnings({"unchecked", "rawtypes"})
	public Page<AssociateInterview> getAssociatesWithFiveOrMore(Pageable page) {
		PageImpl PI = ListToPage.getPage(getAssociatesWithFiveOrMore(), page);
		return PI;
	}
	
	@Override
	public Interview findById(Integer id) {
		
		Interview res = null;
		
		try {
			res = interviewRepo.findById(id).get();
			
		} catch(NoSuchElementException e) {
			
			throw new ResourceNotFoundException("findById failed to find an interview of id: " + id);
		}
		
		return res;
	}

	@Override
    public Interview addAssociateInput(NewAssociateInput a) {
        
        int interviewNumber = a.getInterviewId();
        Interview temp = this.findById(interviewNumber);     
        AssociateInput ai = new AssociateInput(0, a.getReceivedNotifications(), a.isDescriptionProvided(), temp, a.getInterviewFormat(), 
        a.getProposedFormat());

		temp.setAssociateInput(ai);
		associateRepo.save(ai);
	
		return temp;
    }
	
	
	public InterviewFormat findFormatById(Integer id) {
		
		InterviewFormat res = null;

		try {
			res = interviewFormatRepo.findById(id).get();
		
		} catch(NoSuchElementException e) {
			
			throw new ResourceNotFoundException("findFormatById failed to find an InterviewFormat of id: " + id);
		}
		
		return res;
	}
	
	@Override
	public FeedbackStatus findStatusById(Integer id) {
		
		FeedbackStatus res = null;
		
		try {
			res = feedbackStatusRepo.findById(id).get();
		
		} catch(Exception e) {
			
			throw new ResourceNotFoundException("findStatusById failed to find a FeedbackStatus of id " + id);
		}
		
		return res;
	}

	public Interview setFeedback(FeedbackData f) {

		FeedbackStatus status;
		InterviewFormat format;
		Date reqDate;
		String fText;
		Date recDate;
		Date delDate;
		if(f.getStatusId() == 0) {
			status = this.findStatusById(1);
		}
		else {
			status = this.findStatusById(f.getStatusId());
		}
		if(f.getFormat() == 0) {
			format = null;
		}
		else {
			format = this.findFormatById(f.getFormat());
		}
		if(f.getFeedbackRequestedDate() == 0) {
			reqDate = null;
		}
		else {
			reqDate = new Date(f.getFeedbackRequestedDate());
		}
		if(f.getFeedbackText() == null) {
			fText = null;
		}
		else {
			fText = f.getFeedbackText();
		}
		if(f.getFeedbackReceivedDate() == 0) {
			recDate = null;
		}
		else {
			recDate = new Date(f.getFeedbackReceivedDate());
		}
		if(f.getFeedbackDeliveredDate() == 0) {
			delDate = null;
		}
		else {
			delDate = new Date(f.getFeedbackDeliveredDate());
		}
		
		InterviewFeedback interviewFeedback = new InterviewFeedback(0, reqDate, fText, recDate, delDate, status, format);
		
		Interview i = this.findById(f.getInterviewId());
		
		interviewFeedback = feedbackRepo.save(interviewFeedback);
		i.setFeedback(interviewFeedback);
		return save(i);
	}
	
	public InterviewFeedback updateFeedback(Integer i, FeedbackData f) {
		FeedbackStatus status;
		InterviewFormat format;
		Date reqDate;
		String fText;
		Date recDate;
		Date delDate;
		if(f.getStatusId() == 0) {
			status = this.findStatusById(1);
		}
		else {
			status = this.findStatusById(f.getStatusId());
		}
		if(f.getFormat() == 0) {
			format = null;
		}
		else {
			format = this.findFormatById(f.getFormat());
		}
		if(f.getFeedbackRequestedDate() == 0) {
			reqDate = null;
		}
		else {
			reqDate = new Date(f.getFeedbackRequestedDate());
		}
		if(f.getFeedbackText() == null) {
			fText = null;
		}
		else {
			fText = f.getFeedbackText();
		}
		if(f.getFeedbackReceivedDate() == 0) {
			recDate = null;
		}
		else {
			recDate = new Date(f.getFeedbackReceivedDate());
		}
		if(f.getFeedbackDeliveredDate() == 0) {
			delDate = null;
		}
		else {
			delDate = new Date(f.getFeedbackDeliveredDate());
		}
		InterviewFeedback interviewFeedback = new InterviewFeedback(i, reqDate, fText, recDate, delDate, status, format);
		interviewFeedback = feedbackRepo.save(interviewFeedback);
		return interviewFeedback;
	}
  
	@Override
	public List<User> getAssociateNeedFeedback() {
		List<Interview> interviews = interviewRepo.findAll();
		Set<String> needFeedback = new TreeSet<String>();
		List<User> associates = new ArrayList<User>();
		
		for(Interview I: interviews) {
			if(I.getFeedback() != null && I.getFeedback().getFeedbackDelivered() == null) {
				needFeedback.add(I.getAssociateEmail());
			}
		}
		for(String N: needFeedback) {
			try {
				associates.add(userClient.findByEmail(N).getBody());
			} catch (Exception E) {
				System.out.println(E);
			}
		}
		return associates;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	@Override
	public Page<User> getAssociateNeedFeedback(Pageable page) {
		PageImpl PI = ListToPage.getPage(getAssociateNeedFeedback(), page);
		return PI;
	}

	@Override
	public Integer[] getAssociateNeedFeedbackChart() {
		List<Interview> interviews = interviewRepo.findAll();
		Integer[] feedbackChart = {0,0,0,0,0};
		
		feedbackChart[0] = interviews.size();		// [0] is the total number of interviews
		
		for(Interview I: interviews) {

			if(I.getFeedback() == null) {
				feedbackChart[1]++;					// [1] is the number of interviews with no feedback requested
			} else {
				feedbackChart[2]++;					// [2] is the number of interviews with feedback requested
				
				if(I.getFeedback().getFeedbackReceived() != null) {
					
					if(I.getFeedback().getFeedbackDelivered() != null) {
						feedbackChart[3]++;			// [3] is the number of interviews that received feedback that hasn't been delivered to associate
					} else {
						feedbackChart[4]++;			// [4] is the number of interviews that received feedback that has been delivered to associate
					}
				}
			}
		}
		return feedbackChart;
	}

	public List<Interview> getAllInterviewsWithin24HourNoticeAssociate(){
		//find all interviews
		List<Interview> allUsers = interviewRepo.findAll();
		//find all interviews where the users were notified in advance
		ArrayList<Interview> allNotifiedUsers = new ArrayList<Interview>();

		//build a new list iteratively for allNotifiedUsers
		for (Interview i : allUsers)
		{
			if (i.getAssociateInput() == null)
				System.out.println("This interview has no associate input");
			if (i.getAssociateInput() != null)
				if (i.getAssociateInput().getReceivedNotifications() == null)
					System.out.println("This interview has associate input but no received notifications date");
			
			//check of non null
			if (i.getAssociateInput() != null)
				//check of non null
				if (i.getAssociateInput().getReceivedNotifications() != null)
				{
					//Singleton Calendar
					Calendar cal = Calendar.getInstance();
					//Set time on calendar to current receivedNotifications date
					cal.setTime(i.getScheduled());
					//Add 24 Hours to the current date
					cal.add(Calendar.DATE, -1);
					//Calculate a new date, one day from the receivedNotifications
					Date oneDayBefore = cal.getTime();
					//If meets criteria, push to new list
					if (i.getAssociateInput().getReceivedNotifications().before(oneDayBefore) || !(i.getAssociateInput().getReceivedNotifications().after(oneDayBefore))) {
						allNotifiedUsers.add(i);
					}
					System.out.println("getScheduled: "+i.getScheduled()+" oneDayBefore: "+oneDayBefore+" Associate: "+i.getAssociateInput().getReceivedNotifications());
					System.out.println(i.getAssociateInput().getReceivedNotifications().before(oneDayBefore)+" vs "+(!(i.getAssociateInput().getReceivedNotifications().after(oneDayBefore))));
				}
		}
        return allNotifiedUsers;
	}
	
	public List<Integer> getInterviewsWithin24HourNoticeAssociate(){
		//find all interviews
		List<Interview> allNotifiedUsers = getAllInterviewsWithin24HourNoticeAssociate();
		//return
		List<Integer> returning;		
		//count only interviews that are within 24 hour notice
		int countNotified = allNotifiedUsers.size();
		returning = Arrays.asList(interviewRepo.findAll().size(), countNotified);
        return returning;
	}
	
	public List<Interview> getAllInterviewsWithin24HourNoticeManager(){
		//find all interviews
		List<Interview> allUsers = interviewRepo.findAll();
		//find all interviews where the users were notified in advance
		ArrayList<Interview> allNotifiedUsers = new ArrayList<Interview>();

		//build a new list iteratively for allNotifiedUsers
		for (Interview i : allUsers)
		{
			if (i.getNotified() == null)
			{
				System.out.println("This interview has no manager input");
			}
			
			
			//check of non null
				if (i.getNotified() != null)
				{
					//Singleton Calendar
					Calendar cal = Calendar.getInstance();
					//Set time on calendar to current receivedNotifications date
					cal.setTime(i.getScheduled());
					//Add 24 Hours to the current date
					cal.add(Calendar.DATE, -1);
					//Calculate a new date, one day from the receivedNotifications
					Date oneDayBefore = cal.getTime();
					//If meets criteria, push to new list
					if (i.getNotified().before(oneDayBefore) || !(i.getNotified().after(oneDayBefore))){
						allNotifiedUsers.add(i);
					}
			
					System.out.println("getScheduled: "+i.getScheduled()+" oneDayBefore: "+oneDayBefore+" Manager: "+i.getNotified());
					System.out.println(i.getNotified().before(oneDayBefore)+" vs "+(!(i.getNotified().after(oneDayBefore))));
				}
				
		}
        return allNotifiedUsers;
	}
	
	public List<Integer> getInterviewsWithin24HourNoticeManager() {
		//find all interviews
		List<Interview> allNotifiedUsers = getAllInterviewsWithin24HourNoticeManager();
		//return
		List<Integer> returning;		
		//count only interviews that are within 24 hour notice
		int countNotified = allNotifiedUsers.size();
		returning = Arrays.asList(interviewRepo.findAll().size(), countNotified);
        return returning;
    }

	private List<Interview24Hour> getAll24HourNoticeWithoutName(){
		List<Interview> DataIn = interviewRepo.findAll();
		System.out.println(DataIn);
		List<Interview24Hour> DataOut = new ArrayList<Interview24Hour>();
		for(Interview I: DataIn) {
			DataOut.add(new Interview24Hour(I));
		}
		return DataOut;
	}
	
	public List<Interview24Hour> getAll24HourNotice(){
		List<Interview24Hour> Data= getAll24HourNoticeWithoutName();
		
		for(Interview24Hour I: Data) {
			try {
				User U = userClient.findByEmail(I.getAssocEmail()).getBody();
				System.out.println(U);
				I.setAssocName(U.getFirstName()+" "+U.getLastName());
			} catch (Exception E){
				System.out.println(E);
			}
		}
		
		return Data;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public Page<Interview24Hour> getAll24HourNotice(Pageable page) {
		PageImpl PI = ListToPage.getPage(getAll24HourNotice(), page);
		return PI;
	}

	private List<InterviewAssociateJobData> getAllJDNoName(){
		List<Interview> DataIn = interviewRepo.findAll();
		System.out.println(DataIn);
		List<InterviewAssociateJobData> DataOut = new ArrayList<InterviewAssociateJobData>();
		for(Interview I: DataIn) {
			DataOut.add(new InterviewAssociateJobData(I));
		}
		return DataOut;
	}
	
	public List<InterviewAssociateJobData> getAllJD(){
		List<InterviewAssociateJobData> Data= getAllJDNoName();
		
		for(InterviewAssociateJobData I: Data) {
			try {
				User U = userClient.findByEmail(I.getAssocEmail()).getBody();
				System.out.println(U);
				I.setAssocName(U.getFirstName()+" "+U.getLastName());
			} catch (Exception E){
				System.out.println(E);
			}
		}
		
		return Data;
	}

	@SuppressWarnings({"unchecked", "rawtypes"})
	public Page<InterviewAssociateJobData> getAllJD(Pageable page) {
		PageImpl PI = ListToPage.getPage(getAllJD(), page);
		return PI;
	}
	
	@Override
	public InterviewFeedback getInterviewFeedbackByInterviewID(int interviewId) {
		return this.findById(interviewId).getFeedback();
	}

	@Override
	public Interview markReviewed(int interviewId) {
		Interview I = this.findById(interviewId);
		I.setReviewed(new Date(System.currentTimeMillis()));
		return interviewRepo.save(I);
	}

	@Override
	public Interview findByAssociateEmail(String s) {
		return null;
	}

	@Override
	public Interview findByManagerEmail(String s) {
		return null;
	}

	@Override
	public List<Interview> findByScheduledWeek(Date date) {
		
		// Set up Calendar instance and set the date to the passed in date
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		// Set the time to Sunday at midnight of the beginning of the week
		int weekYear = cal.getWeekYear();
		int weekOfYear = cal.get(Calendar.WEEK_OF_YEAR);
		cal.setWeekDate(weekYear, weekOfYear, Calendar.SUNDAY);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		// Use that Sunday as the beginning date and the following
		// Sunday at midnight (168 hours later) as the time frame
		Date first = cal.getTime();
		cal.add(Calendar.HOUR, 168);
		Date last = cal.getTime();
		
		return interviewRepo.findByScheduledBetween(first, last);
	}


	@Override
	@SuppressWarnings({"unchecked"})
	public Page<FeedbackStat> findFeedbackStats(Pageable page) {
		List<Interview> interviewStats = interviewRepo.findByFeedbackIsNotNullOrderByFeedbackFeedbackRequested();
		ArrayList<String> emailList = new ArrayList<>(interviewStats.size() * 2);
		ArrayList<FeedbackStat> returnList = new ArrayList<>(interviewStats.size());
		interviewStats.forEach(inter -> {
			emailList.add(inter.getManagerEmail());
			emailList.add(inter.getAssociateEmail());
			});
		try {
			EmailList eList = new EmailList(emailList);
			ArrayList<User> uList = userClient.getUsersByEmails(eList);
			ArrayList<String> names = new ArrayList<>(2);
			ArrayList<User> users = new ArrayList<>(2);
			names.add("");
			names.add("");
			users.add(null);
			users.add(null);
			interviewStats.forEach(inter -> {
				names.set(0, "");
				names.set(1, "");
				users.set(0, null);
				users.set(1, null);
				uList.forEach(user -> {
					if(user.getEmail().equals(inter.getManagerEmail())) {
						users.set(0, user);
					} else if(user.getEmail().equals(inter.getAssociateEmail())) {
						users.set(1, user);
					}
				});
				if(users.get(0) != null) {
					names.set(0, users.get(0).getFirstName() + " " + users.get(0).getLastName());
				}
				if(users.get(1) != null) {
					names.set(1, users.get(1).getFirstName() + " " + users.get(1).getLastName());
				}
				returnList.add(new FeedbackStat(inter, names.get(0).trim(), names.get(1).trim()));
			});
		} catch (Exception e) {
			System.out.println(e);
			returnList.clear();
			interviewStats.forEach(inter -> {
				returnList.add(new FeedbackStat(inter, "", ""));
			});
		}
		// because I am not used to dealing with dto conversions and pages
		// some of the metadata in PageImpl may be wrong.
		return ListToPage.getPage(returnList, page);

	}

	@Override
	public User getByEmail(String email) {
		// TODO Auto-generated method stub
		return userClient.findByEmail(email).getBody();
	}

}
