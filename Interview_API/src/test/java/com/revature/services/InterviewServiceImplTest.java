package com.revature.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

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
	
	@MockBean
	CognitoUtil cognitoUtil;
	
	@Mock
	IUserClient userClient;
	
	@Mock
	InterviewRepo interviewRepo;
	
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
	public void getByEmailTest() {
		// Setup
		String email = "some-email";
		User user = new User();
		ResponseEntity<User> response = ResponseEntity.ok(user);
		
		// Stub
		Mockito.when(userClient.findByEmail(email)).thenReturn(response);
		
		// Call
		User returnedUser = interviewService.getByEmail(email);
		
		// Verify findByEmail called with email
		Mockito.verify(userClient).findByEmail(email);
		
		// Assert that getByEmail returns the user passed as the body from findByEmail
		assertThat(user).isEqualTo(returnedUser);
	}
	
	
}
