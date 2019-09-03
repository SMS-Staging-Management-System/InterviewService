package com.revature.services;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import com.revature.models.Interview;
import com.revature.repos.InterviewRepo;

// to run this test you will need to set an environment variable in the eclipse tools run configuration
// for this file - set spring.profiles to dev
// this test uses the sql startup file in /src/test/resources/data.sql when the class test starts to 
// create and load data into an h2 in memory database

@RunWith(SpringRunner.class)
@SpringBootTest
public class InterviewServiceIntegrationTests {

	@Autowired
	InterviewRepo interviewRepo;

	@Autowired
	InterviewService interviewService;

	// use these defaults for the Pageable object
	String direction = "ASC";
	String orderBy = "id";
	Integer pageNumber = 0;
	Integer pageSize = 10;

	Sort sorter = new Sort(Sort.Direction.valueOf(direction), orderBy);
	Pageable pageParameters = PageRequest.of(pageNumber, pageSize, sorter);

	
	@Test
	public void testFindAllShouldFilterByManagerEmail() {
		String search = "associateEmail:*,managerEmail:blake,place:*,client:*,staging:*&orderBy=id&direction=ASC&pageSize=50";
		Page<Interview> results = interviewService.findAllWithFilters(search, pageParameters);
		List<Interview> contents = results.getContent();
		assert(contents.size()>1);
		for (int i = 0; i < contents.size(); i++) {
			System.out.println("filter by manager email tests: "+i+" "+contents.get(i));
			assert (contents.get(i).getManagerEmail().contains("blake"));
		}
	}

	@Test
	public void testFindAllShouldFilterByAssociateEmail() {

		String search = "associateEmail:er,managerEmail:*,place:*,client:*,staging:*&orderBy=id&direction=ASC&pageSize=50";
		Page<Interview> results = interviewService.findAllWithFilters(search, pageParameters);
		List<Interview> contents = results.getContent();
		assert(contents.size()>1);
		for (int i = 0; i < contents.size(); i++) {
			System.out.println("filter by associate email tests: "+i+" "+contents.get(i));
			assert (contents.get(i).getAssociateEmail().contains("er"));
		}
	}

	@Test
	public void testFindAllShouldFilterByPlace() {

		// some commons places in database: place lane
		String search = "associateEmail:*,managerEmail:*,place:USF,client:*,staging:*&orderBy=id&direction=ASC&pageSize=50";
		Page<Interview> results = interviewService.findAllWithFilters(search, pageParameters);
		List<Interview> contents = results.getContent();
		assert(contents.size()>1);
		for (int i = 0; i < contents.size(); i++) {
			System.out.println("filter by place tests: "+i+" "+contents.get(i));
			assert (contents.get(i).getPlace().contains("USF"));
		}
	}

	@Test
	public void testFindAllShouldFilterByClient() {

		// e is extremely common
		String search = "associateEmail:*,managerEmail:*,place:*,client:Dell,staging:*&orderBy=id&direction=ASC&pageSize=50";
		Page<Interview> results = interviewService.findAllWithFilters(search, pageParameters);
		List<Interview> contents = results.getContent();
		assert(contents.size()>1);
		for (int i = 0; i < contents.size(); i++) {
			System.out.println("filter by client tests: "+i+" "+contents.get(i));
			assert (contents.get(i).getClient().getClientName().contains("Dell"));
		}
	}
	
	@Test
	public void testFindAllShouldBeCaseInsensitive() {

		// e is extremely common
		String search = "associateEmail:*,managerEmail:*,place:USF,client:*,staging:*&orderBy=id&direction=ASC&pageSize=50";
		Page<Interview> results = interviewService.findAllWithFilters(search, pageParameters);
		List<Interview> contents = results.getContent();
		for (int i = 0; i < contents.size(); i++) {
			System.out.println("case insensitive tests: "+contents.get(i));
		}
		String search2 = "associateEmail:*,managerEmail:*,place:usf,client:*,staging:*&orderBy=id&direction=ASC&pageSize=50";
		Page<Interview> results2 = interviewService.findAllWithFilters(search2, pageParameters);
		List<Interview> contents2 = results2.getContent();
		for (int i = 0; i < contents2.size(); i++) {
			System.out.println("case insensitive tests: "+contents2.get(i));
		}
		
		for (int i = 0; i < contents.size(); i++) {
			System.out.println("case insensitive tests: "+contents.get(i)+"..."+contents2.get(i));
			assert (contents.get(i).getManagerEmail() == contents2.get(i).getManagerEmail());
		}
	}


}