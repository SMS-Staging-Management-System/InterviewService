package com.revature.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.cognito.utils.CognitoUtil;
import com.revature.models.Interview;
import com.revature.repos.InterviewRepo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InterviewServiceImplTest {
	
	@InjectMocks
	private InterviewServiceImpl interviewServiceTest = new InterviewServiceImpl();
	
	@MockBean
	CognitoUtil cognitoUtil;
	
	@Mock
	InterviewRepo interviewRepo;
	
	@Test
	public void saveTest() {
		assertThat(interviewServiceTest.save(new Interview())).isNull();
	}
}
