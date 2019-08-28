package com.revature.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.InterviewFormat;

public interface InterviewFormatRepo extends JpaRepository<InterviewFormat, Integer> {

}
