package com.revature.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.revature.models.FeedbackStatus;


public interface FeedbackStatusRepo extends JpaRepository<FeedbackStatus, Integer> {

}
