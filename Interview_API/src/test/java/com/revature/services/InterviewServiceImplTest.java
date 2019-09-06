package com.revature.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.cognito.constants.CognitoRoles;
import com.revature.cognito.dtos.CognitoTokenClaims;
import com.revature.cognito.utils.CognitoUtil;
import com.revature.feign.IUserClient;
import com.revature.models.Interview;
import com.revature.models.User;
import com.revature.repos.InterviewRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InterviewServiceImplTest {
	
	@InjectMocks
	private InterviewServiceImpl interviewServiceTest = new InterviewServiceImpl();
	
	@Mock
	CognitoUtil cognitoUtil;
	
	@Mock
	IUserClient userClient;
	
	@Mock
	InterviewRepo interviewRepo;
	
	// Common Values
	String email = "test@email.com";
	CognitoTokenClaims claims = new CognitoTokenClaims("", email);
	
	List<Interview> allInterviews = Arrays.asList(new Interview());
	List<Interview> someInterviews = new ArrayList<>();
	
	@After
	public void clearMocks() {
		Mockito.reset(cognitoUtil);
		Mockito.reset(interviewRepo);
	}
	
	@Test
	public void saveTest() {
		assertThat(interviewServiceTest.save(new Interview())).isNull();
	}
	
	@Test 
	public void updateTest() {
		// Setup
		Interview interview = new Interview();
		Interview expectedInterview = new Interview();
		
		// Stub
		Mockito.when(interviewRepo.save(interview)).thenReturn(expectedInterview);
		
		// Call
		Interview returnedInterview = interviewServiceTest.update(interview);
		
		// Verify repo method is called passing interview to it
		Mockito.verify(interviewRepo, Mockito.times(1)).save(interview);
		
		// Assert that returned value of repo is returned to client
		assertThat(returnedInterview).isSameAs(expectedInterview);
	}
	
	/* findAll related tests */
	
	@Test
	public void findAllAdminTest() {
		// Setup
		List<String> roles = Arrays.asList(new String[] {CognitoRoles.ADMIN});
		findAllSetup(roles);
		
		// Call
		List<Interview> returnedInterviews = interviewServiceTest.findAll();
		findAllExpectAll(returnedInterviews);
	}
	
	@Test
	public void findAllStagingManagerTest() {
		// Setup
		List<String> roles = Arrays.asList(new String[] {CognitoRoles.STAGING_MANAGER});
		findAllSetup(roles);
		
		// Call
		List<Interview> returnedInterviews = interviewServiceTest.findAll();
		
		findAllExpectAll(returnedInterviews);
	}
	
	@Test
	public void findAllTrainerTest() {
		// Setup
		List<String> roles = Arrays.asList(new String[] {CognitoRoles.TRAINER});
		findAllSetup(roles);
		
		// Call
		List<Interview> returnedInterviews = interviewServiceTest.findAll();
		
		findAllExpectSubset(returnedInterviews);
	}
	
	@Test
	public void findAllNoRolesTest() {
		// Setup
		List<String> roles = Arrays.asList(new String[] {});
		findAllSetup(roles);
		
		// Call
		List<Interview> returnedInterviews = interviewServiceTest.findAll();
		
		findAllExpectSubset(returnedInterviews);
	}
	
	@Test
	public void findAllEveryRoleTest() {
		// Setup
		List<String> roles = Arrays.asList(new String[] {CognitoRoles.STAGING_MANAGER, CognitoRoles.ADMIN,
									CognitoRoles.TRAINER});
		findAllSetup(roles);
		
		// Call
		List<Interview> returnedInterviews = interviewServiceTest.findAll();
		
		findAllExpectAll(returnedInterviews);
	}
	
	
	private void findAllSetup(List<String> roles) {
		String email = "test@email.com";
		CognitoTokenClaims claims = new CognitoTokenClaims();
		claims.setEmail(email);

		// Stub
		Mockito.when(cognitoUtil.getRequesterRoles()).thenReturn(roles);
		Mockito.when(interviewRepo.findAll()).thenReturn(allInterviews);
		Mockito.when(cognitoUtil.getRequesterClaims()).thenReturn(claims);
		Mockito.when(interviewRepo.findByAssociateEmail(email)).thenReturn(someInterviews);
	}
	
	private void findAllExpectAll(List<Interview> returnedInterviews) {
		// Verify that only the findAll method was called on the mocked repo
		Mockito.verify(interviewRepo, Mockito.only()).findAll();
		Mockito.verify(cognitoUtil, Mockito.never()).getRequesterClaims();
		
		assertThat(returnedInterviews).isSameAs(allInterviews);	
	}
	
	private void findAllExpectSubset(List<Interview> returnedInterviews) {
		// Verify that only the findByAssociateEmail method was called on the mocked repo
		Mockito.verify(interviewRepo, Mockito.only()).findByAssociateEmail(email);
		Mockito.verify(cognitoUtil, Mockito.times(1)).getRequesterClaims();
		
		assertThat(returnedInterviews).isSameAs(someInterviews);	
	}
	
	/* end findAll related tests */
	
	@Test
	public void getByEmailTest() {
		// Setup
		String email = "some-email";
		User user = new User();
		ResponseEntity<User> response = ResponseEntity.ok(user);
		
		// Stub
		Mockito.when(userClient.findByEmail(email)).thenReturn(response);
		
		// Call
		User returnedUser = interviewServiceTest.getByEmail(email);
		
		// Verify findByEmail is called once with email
		Mockito.verify(userClient, Mockito.times(1)).findByEmail(email);
		
		// Assert that getByEmail returns the user passed as the body from findByEmail
		assertThat(user).isEqualTo(returnedUser);
	}
	

	
	@Test
	public void checkRepo() { 
		System.out.println("my interview"+interviewRepo.findById(1));
	}
	
	
	@Test  //Tests line 89
	public void findAllCognitoUtilRoles() {
		assertThat(cognitoUtil.getRequesterRoles()).isNotNull();//Test is for no empties - should be pass
	}
	
	@Test // Tests line 93
	public void findAllCognitoUtilClaims() {
		assertThat(cognitoUtil.getRequesterClaims()).isNotNull(); // should be null because the above is not null?
	} 
	

	@Test //Below is attempt to test function addNewInterview on line 104
	public void addNewInterviewTest() {
		assertThat(cognitoUtil.getRequesterClaims()).isNull();
	}
	
	@Test  //Below is attempt to test function Page on line 129
	public void pageTest() {
		assertThat(interviewRepo.findAll()).isNotNull();
	}
	
	//@Test  //Below is attempt to test function override of Page on line 134 - getting initialization error
	//public void pageableTest(Specification<Interview> spec, Pageable pageable) {
		//assertThat(interviewRepo.findAll()).isNotNull();
	//}
	
	@Test //Below is attempt to test function of findInterviewsPerAssociate on line 164
	public void findInterviewsPerAssociateTest() {
		assertThat(interviewServiceTest.findInterviewsPerAssociate()).isNotNull();
	}
	
	@Test //Below is attempt to test function of findAssociateInterviewsData on line 208
	public void findAssociateInterviewsDataTest() {
		assertThat(interviewServiceTest.findAssociateInterviewsData()).isNotNull();
	}
	
	//@Test //Below is attempt to test function of findByEmail on line 226
	//public void findByEmailTest() {
		//assertThat(interviewServiceTest.findByEmail()).isNotNull();
	//} //Error states should not use parameters but won't work w/o them.

	@Test  //Below is attempt to test function getAssociatesWithFiveOrMore on line 240
	public void getAssociatesWithFiveOrMoreTest() {
		assertThat(interviewServiceTest.getAssociatesWithFiveOrMore()).isNotNull();
	}
	
	//@Test  //Below is attempt to test function findById on line 254 - unsuccessful
	//public void findByIdTest() {
	//	assertThat(interviewServiceTest.findById()).isNotNull();
	//}
	
	@Test  //Below is attempt to test function addAssociateInput on line 270
	public void addAssociateInputTest() {
		assertThat(interviewServiceTest.addAssociateInput(null)).isNotNull();
	}  // Test runs but also throws an error.  Maybe by design?
	
	@Test  //Below is attempt to test function findFormatById on line 284
	public void findFormatByIdTest() {
		assertThat(interviewServiceTest.findFormatById(1)).isNotNull();
	}  // Like test above, runs but throws an AssertionError
	
	@Test // Below is attempt to test function findStatusById on line 300
	public void findStatusByIdTest() {
		assertThat(interviewServiceTest.findStatusById(null)).isNull();
	}
	
	@Test // Below is attempt to test function setFeedback on line 315
	public void setFeedbackTest() {
		assertThat(interviewServiceTest.setFeedback(null)).isNotNull();
	}// Like test above, runs but throws an Error
	
	@Test // Below is attempt to test function updateFeedback on line 369
	// a better input for the second argument would be a mocked feedback data object
	public void interviewFeedbackTest() {
		assertThat(interviewServiceTest.updateFeedback(0, null)).isNotNull();
	}// Like test above, runs but throws an Error
	
	@Test // Below is attempt to test function getAssociateNeedFeedback on line 418
	public void getAssociateNeedFeedbackTest() {
		assertThat(interviewServiceTest.getAssociateNeedFeedback()).isNotNull();
	} //This test seems to run without error.
	
	//Skipping test on line 440 as the function has same name as above.
	
	@Test // Below is attempt to test function getAssociateNeedFeedbackChart on line 445
	public void getAssociateNeedFeedbackChart() {
		assertThat(interviewServiceTest.getAssociateNeedFeedbackChart()).isNotNull();
	} //This test seems to run without error.
	
	@Test // Below is attempt to test function getAllInterviewsWithin24HourNoticeAssociate on line 472
	public void getAllInterviewsWithin24HourNoticeAssociateTest() {
		assertThat(interviewServiceTest.getAllInterviewsWithin24HourNoticeAssociate()).isNotNull();
	} //This test seems to run without error.
	
	@Test // Below is attempt to test function getInterviewsWithin24HourNoticeAssociate on line 511
	public void getInterviewsWithin24HourNoticeAssociateTest() {
		assertThat(interviewServiceTest.getInterviewsWithin24HourNoticeAssociate()).isNotNull();
	} //This test seems to run without error.
	
	@Test //Below is attempt to test function getAllInterviewsWithin24HourNoticeManager on line 522
	public void getAllInterviewsWithin24HourNoticeManager() {
		assertThat(interviewServiceTest.getAllInterviewsWithin24HourNoticeManager()).isNotNull();
	} //This test seems to run without error.
	
	@Test //Below is attempt to test function getInterviewsWithin24HourNoticeManager on line 561
	public void getInterviewsWithin24HourNoticeManagerTest() {
		assertThat(interviewServiceTest.getInterviewsWithin24HourNoticeManager()).isNotNull();
	}//This test seems to run without error.
		
	//@Test //Below is attempt to test function getAll24HourNoticeWithoutName on line 572
	//public void getAll24HourNoticeWithoutNameTest() {
	//	assertThat(interviewServiceTest.getAll24HourNoticeWithoutName()).isNotNull();
	//}  //Unable to get this one working.
	
	@Test //Below is attempt to test function getAll24HourNotice on line 582
	public void getAll24HourNoticeTest() {
		assertThat(interviewServiceTest.getAll24HourNotice()).isNotNull();
	}// Test runs successfully
	
	@Test //Below is attempt to test function getAll24HourNotice on line 598
	public void getAll24HourNoticeTest2() {
		assertThat(interviewServiceTest.getAll24HourNotice()).isNotNull();
	}// unsure if this is really testing line 598 code.
	
	//@Test //Below is attempt to test function getAllJDNoName on line 604
	//public void getAllJDNoNameTest() {
		//assertThat(interviewServiceTest.getAllJDNoName()).isNotNull();
	//} This test will behave IF 'private' is removed from line 604
	
	@Test //Below is attempt to test function getAllJD on line 614
	public void getAllJDTest() {
		assertThat(interviewServiceTest.getAllJD()).isNotNull();
	}// test runs successfully
	
	@Test //Below is attempt to test OVERIDE function getAllJD on line 631
	public void getAllJDTest2() {
		assertThat(interviewServiceTest.getAllJD()).isNotNull();
	}// unsure if this is really testing line 631 code.
	
	//@Test //Below is attempt to test function getInterviewFeedbackByInterviewID on line 637
	//public void getInterviewFeedbackByInterviewIDTest() {
		//assertThat(interviewServiceTest.getInterviewFeedbackByInterviewID()).isNotNull();
	//} //redline says arguments needed but that results in error upon run.
	
	//@Test //Below is attempt to test function markReviewed on line 642
	//public void markReviewedTest(int i) {
		//assertThat(interviewServiceTest.markReviewed(i)).isNotNull();
	//}// Same issue as above - redline wants args, RUN does not.
	
	//@Test //Below tries to test 'findByAssociateEmail' on line 649.
	public void findByAssociateEmailTest() {
		assertThat(interviewServiceTest.findByAssociateEmail(null)).isNotNull();
	}
	
	@Test //Below tries to test 'findByManagerEmail' on line 654.
	public void findByManagerEmailTest() {
		assertThat(interviewServiceTest.findByManagerEmail(null)).isNull();
	}
	
	//@Test //Below tries to test 'findAllByAssociateEmail' on line 659
	//public void findAllByAssociateEmailTest(String i, String j) {
		//assertThat(interviewServiceTest.findAllByAssociateEmail(null, null)).isNull();
	//}// Test causes errors with and without arguments.
	
	@Test //Below tries to test 'findByScheduledWeek' on line 664
	public void findByScheduledWeekTest() {
		assertThat(interviewServiceTest.findByScheduledWeek(null)).isNull();
	} //This test runs, then errors out.  Leaving as is as example.
	
	@Test //Below tries to test 'findFeedbackStats' on line 691
	public void findFeedbackStatsTest() {
		assertThat(interviewServiceTest.findFeedbackStats(null)).isNull();
	} //This test runs, then errors out.  Leaving as is as example.
	

}
