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
	private InterviewServiceImpl interviewService = new InterviewServiceImpl();
	
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
		assertThat(interviewService.save(new Interview())).isNull();
	}
	
	@Test 
	public void updateTest() {
		// Setup
		Interview interview = new Interview();
		Interview expectedInterview = new Interview();
		
		// Stub
		Mockito.when(interviewRepo.save(interview)).thenReturn(expectedInterview);
		
		// Call
		Interview returnedInterview = interviewService.update(interview);
		
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
		List<Interview> returnedInterviews = interviewService.findAll();
		findAllExpectAll(returnedInterviews);
	}
	
	@Test
	public void findAllStagingManagerTest() {
		// Setup
		List<String> roles = Arrays.asList(new String[] {CognitoRoles.STAGING_MANAGER});
		findAllSetup(roles);
		
		// Call
		List<Interview> returnedInterviews = interviewService.findAll();
		
		findAllExpectAll(returnedInterviews);
	}
	
	@Test
	public void findAllTrainerTest() {
		// Setup
		List<String> roles = Arrays.asList(new String[] {CognitoRoles.TRAINER});
		findAllSetup(roles);
		
		// Call
		List<Interview> returnedInterviews = interviewService.findAll();
		
		findAllExpectSubset(returnedInterviews);
	}
	
	@Test
	public void findAllNoRolesTest() {
		// Setup
		List<String> roles = Arrays.asList(new String[] {});
		findAllSetup(roles);
		
		// Call
		List<Interview> returnedInterviews = interviewService.findAll();
		
		findAllExpectSubset(returnedInterviews);
	}
	
	@Test
	public void findAllEveryRoleTest() {
		// Setup
		List<String> roles = Arrays.asList(new String[] {CognitoRoles.STAGING_MANAGER, CognitoRoles.ADMIN,
									CognitoRoles.TRAINER});
		findAllSetup(roles);
		
		// Call
		List<Interview> returnedInterviews = interviewService.findAll();
		
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
		User returnedUser = interviewService.getByEmail(email);
		
		// Verify findByEmail is called once with email
		Mockito.verify(userClient, Mockito.times(1)).findByEmail(email);
		
		// Assert that getByEmail returns the user passed as the body from findByEmail
		assertThat(user).isEqualTo(returnedUser);
	}
	
}
