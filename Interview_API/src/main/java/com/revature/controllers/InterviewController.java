package com.revature.controllers;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.cognito.annotations.CognitoAuth;
import com.revature.dtos.AssociateInterview;
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
import com.revature.models.Interview;
import com.revature.models.InterviewFeedback;
import com.revature.models.User;
import com.revature.services.InterviewService;

@RestController
@RequestMapping("interview")
public class InterviewController {

	@Autowired
	private InterviewService interviewService;

	@Autowired
	private IUserClient userClient;

	@CognitoAuth(roles = {})
	@GetMapping
	public List<Interview> findAll() {
		return interviewService.findAll();
	}

	@CognitoAuth(roles = {})
	@GetMapping("users/{id}")
	public User findById(@PathVariable("id") int id) {
		return userClient.findById(id);
	}


	@CognitoAuth(roles = {})
	@GetMapping("/pages")
	public Page<Interview> getInterviewPageForAssociate(
			@RequestParam(name = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(name = "direction", defaultValue = "ASC") String direction,
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(name = "search") String search) {

		Sort sorter = new Sort(Sort.Direction.valueOf(direction), orderBy);
		Pageable pageParameters = PageRequest.of(pageNumber, pageSize, sorter);
		return interviewService.findAllWithFilters(search, pageParameters);
	}

	// Should return any results that partially match the search string
	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("/page")
	public Page<Interview> getInterviewPage(
			@RequestParam(name = "orderBy", defaultValue = "id") String orderBy,
			@RequestParam(name = "direction", defaultValue = "ASC") String direction,
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "search") String search) {

		Sort sorter = new Sort(Sort.Direction.valueOf(direction), orderBy);
		Pageable pageParameters = PageRequest.of(pageNumber, pageSize, sorter);

		return interviewService.findAllWithFilters(search, pageParameters);
	}

	// returns 2 numbers in a list
	// the first is the number of users
	// the second is the number of users who received 24 hour notice (according to
	// the associate)
	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("reports/request24/associate")
	public List<Integer> getInterviewsWithin24HourNoticeAssociate() {
		return interviewService.getInterviewsWithin24HourNoticeAssociate();
	}

	@CognitoAuth(roles = {})
	@GetMapping("{InterviewId}")
	public Interview getInterviewById(@PathVariable int InterviewId) {
		return interviewService.findById(InterviewId);
	}

	@CognitoAuth(roles = { "staging-manager", "admin" })
	@GetMapping("markReviewed/{InterviewId}")
	public Interview markReviewed(@PathVariable int InterviewId) {
		return interviewService.markReviewed(InterviewId);
	}

	// returns 2 numbers in a list
	// the first is the number of users
	// the second is the number of users who received 24 hour notice (according to
	// the manager)
	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("reports/request24/manager")
	public List<Integer> getInterviewsWithin24HourNoticeManager() {
		return interviewService.getInterviewsWithin24HourNoticeManager();
	}

	@CognitoAuth(roles = {})
	@PostMapping("/saveinterview")
	public Interview newInterview(@Valid @RequestBody Interview i) {
		return interviewService.save(i);
	}

	// @CognitoAuth(roles = {})
	@PostMapping("/new")
	public ResponseEntity<Interview> addNewInterview(@Valid @RequestBody NewInterviewData i) {

		Interview returnedInterview = interviewService.addNewInterview(i);

		return ResponseEntity.ok(returnedInterview);
	}

	@CognitoAuth(roles = {})
	@GetMapping("/test")
	public ResponseEntity<String> test() {
		String o = "failed";
		try {
			System.out.println("userClient");
			System.out.println(userClient);
			o = userClient.findAll().toString();
			System.out.println("userClient.findAll()");
			System.out.println(o);
		} catch (Exception e) {
			System.out.println("exception occurred");
			System.out.println(e);
		}
		return ResponseEntity.ok(o);
	}

	@CognitoAuth(roles = {})
	@GetMapping("/findInterview")
	public Interview findInterviewById(@RequestParam(name = "interviewId", defaultValue = "id") int interviewId) {

		return interviewService.findById(interviewId);
	}

	@CognitoAuth(roles = {})
	@PostMapping("/associateInput")
	public ResponseEntity<Interview> newAssociateInput(@Valid @RequestBody NewAssociateInput a) {

		return ResponseEntity.ok(interviewService.addAssociateInput(a));
	}

	@CognitoAuth(roles = { "staging-manager", "admin" })
	@PostMapping("/feedback")
	public ResponseEntity<Interview> updateInterviewFeedback(@Valid @RequestBody FeedbackData f) {

		Interview result = interviewService.setFeedback(f);

		return ResponseEntity.ok(result);
	}

	@CognitoAuth(roles = {})
	@GetMapping("Feedback/InterviewId/{InterviewId}")
	public InterviewFeedback getInterviewFeedbackByInterviewID(@PathVariable int InterviewId) {
		return interviewService.getInterviewFeedbackByInterviewID(InterviewId);
	}

	@CognitoAuth(roles = { "staging-manager", "admin" })
	@PatchMapping("Feedback/InterviewId/{InterviewId}")
	public ResponseEntity<InterviewFeedback> editInterviewFeedbackByInterviewId(@PathVariable int InterviewId,
			@Valid @RequestBody FeedbackData f) {

		InterviewFeedback result = interviewService.updateFeedback(InterviewId, f);

		return ResponseEntity.ok(result);
	}

	@CognitoAuth(roles = {})
	@GetMapping("reports/InterviewsPerAssociate")
	public List<AssociateInterview> getInterviewsPerAssociate() {
		return interviewService.findInterviewsPerAssociate();
	}

	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("dashboard/interviews/associate/fiveormore")
	public List<AssociateInterview> getAssociatesWithFiveOrMore() {
		return interviewService.getAssociatesWithFiveOrMore();
	}

	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("reports/InterviewsPerAssociate/page")
	public Page<AssociateInterview> getInterviewsPerAssociatePaged(
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {

		Pageable pageParameters = PageRequest.of(pageNumber, pageSize);

		return interviewService.findInterviewsPerAssociate(pageParameters);
	}

	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("reports/InterviewsPerAssociate/chart")
	public NumberOfInterviewsCount getInterviewsPerAssociateStats() {
		return interviewService.findAssociateInterviewsData();
	}

	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("dashboard/interviews/associate/fiveormore/page")
	public Page<AssociateInterview> getAssociatesWithFiveOrMorePaged(
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {

		Pageable pageParameters = PageRequest.of(pageNumber, pageSize);

		return interviewService.getAssociatesWithFiveOrMore(pageParameters);
	}

	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("reports/AssociateNeedFeedback")
	public List<User> getAssociateNeedFeedback() {
		return interviewService.getAssociateNeedFeedback();
	}

	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("reports/AssociateNeedFeedback/page")
	public Page<User> getAssociateNeedFeedbackPaged(
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {

		Pageable pageParameters = PageRequest.of(pageNumber, pageSize);

		return interviewService.getAssociateNeedFeedback(pageParameters);
	}

	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("reports/interview24")
	public List<Interview24Hour> getAll24HourNotice() {
		return interviewService.getAll24HourNotice();
	}

	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("reports/interview24/page")
	public Page<Interview24Hour> getAll24HourNotice(
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {

		Pageable pageParameters = PageRequest.of(pageNumber, pageSize);

		return interviewService.getAll24HourNotice(pageParameters);
	}

	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("reports/interviewJD")
	public List<InterviewAssociateJobData> getAllJD() {
		return interviewService.getAllJD();
	}

	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("reports/interviewJD/page")
	public Page<InterviewAssociateJobData> getAllJD(
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {

		Pageable pageParameters = PageRequest.of(pageNumber, pageSize);

		return interviewService.getAllJD(pageParameters);
	}

	// [0] is the total number of interviews
	// [1] is the number of interviews with no feedback requested
	// [2] is the number of interviews with feedback requested
	// [3] is the number of interviews that received feedback that hasn't been
	// delivered to associate
	// [4] is the number of interviews that received feedback that has been
	// delivered to associate
	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("reports/AssociateNeedFeedback/chart")
	public Integer[] getAssociateNeedFeedbackChart() {
		return interviewService.getAssociateNeedFeedbackChart();
	}

	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("CalendarWeek/{epochDate}")
	public List<Interview> findByCalendarWeek(@PathVariable long epochDate) {

		// Epoch dates are easier to pass, so use epoch date and set date using that
		Date date = new Date(epochDate);
		return interviewService.findByScheduledWeek(date);
	}

	@CognitoAuth(roles = {})
	@GetMapping(value = "email/{email:.+}")
	public ResponseEntity<UserDto> findByEmail(@PathVariable String email) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", "application/json");

		UserDto user = null;

		try {
			user = interviewService.findByEmail(email);

		} catch (Exception e) {

			e.printStackTrace();
		}

		if (user == null) {
			throw new ResourceNotFoundException("Failed to find user of email " + email);
		}

		return new ResponseEntity<UserDto>(user, headers, HttpStatus.OK);
	}

	@CognitoAuth(roles = { "staging-manager", "admin", "trainer" })
	@GetMapping("/reports/FeedbackStats/page")
	public Page<FeedbackStat> fetchFeedbackStats(
			@RequestParam(name = "pageNumber", defaultValue = "0") Integer pageNumber,
			@RequestParam(name = "pageSize", defaultValue = "5") Integer pageSize) {
		return interviewService.findFeedbackStats(PageRequest.of(pageNumber, pageSize));
	}
}
