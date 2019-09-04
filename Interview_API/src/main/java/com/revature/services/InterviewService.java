package com.revature.services;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import com.revature.dtos.AssociateInterview;
import com.revature.dtos.FeedbackData;
import com.revature.dtos.FeedbackStat;
import com.revature.dtos.Interview24Hour;
import com.revature.dtos.InterviewAssociateJobData;
import com.revature.dtos.NewAssociateInput;
import com.revature.dtos.NewInterviewData;
import com.revature.dtos.NumberOfInterviewsCount;
import com.revature.dtos.UserDto;
import com.revature.models.FeedbackStatus;
import com.revature.models.Interview;
import com.revature.models.InterviewFeedback;
import com.revature.models.InterviewFormat;
import com.revature.models.User;

public interface InterviewService {

	Interview save(Interview i);
	Interview update(Interview i);
	Interview delete(Interview i);
	Interview findById(Integer id);
	
	Interview findByAssociateEmail(String s);
	
	Interview findByManagerEmail(String s);
	FeedbackStatus findStatusById(Integer id);
	List<Interview> findAll();
	List<Interview> findAllTest();
	Page<Interview> findAllWithFilters(String search, Pageable pageParameters);
	

	List<Interview> getInterviewsStaging();
	
	Page<Interview> findAll(Specification<Interview> spec, Pageable pageable);
	
	
	Interview addNewInterview(NewInterviewData i);
	Interview addAssociateInput(NewAssociateInput a);
	
	Page<Interview> findAll(Pageable page);
	
	List<AssociateInterview> findInterviewsPerAssociate();
	Page<AssociateInterview> findInterviewsPerAssociate(Pageable page);
	NumberOfInterviewsCount findAssociateInterviewsData();
	List<AssociateInterview> getAssociatesWithFiveOrMore();
	Page<AssociateInterview> getAssociatesWithFiveOrMore(Pageable page);
	List<Integer> getInterviewsWithin24HourNoticeAssociate();
	List<Integer> getInterviewsWithin24HourNoticeManager();
	Interview setFeedback(FeedbackData fm);
	InterviewFeedback updateFeedback(Integer i, FeedbackData f);
	List<User> getAssociateNeedFeedback();
	Page<User> getAssociateNeedFeedback(Pageable page);
	List<Interview24Hour> getAll24HourNotice();
	Page<Interview24Hour> getAll24HourNotice(Pageable page);
	List<InterviewAssociateJobData> getAllJD();
	Page<InterviewAssociateJobData> getAllJD(Pageable page);
	Integer[] getAssociateNeedFeedbackChart();
	InterviewFeedback getInterviewFeedbackByInterviewID(int interviewId);
	Interview markReviewed(int interviewId);
	List<Interview> findByScheduledWeek(Date date);
	InterviewFormat findFormatById(Integer id);
	Page<FeedbackStat> findFeedbackStats(Pageable page);
	UserDto findByEmail(String email);
	User getByEmail(String email);
}
