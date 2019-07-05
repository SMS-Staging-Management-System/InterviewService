package com.revature.services;

import javax.persistence.criteria.Join;

import org.springframework.data.jpa.domain.Specification;

import com.revature.models.Interview;

public class InterviewSpecifications{
	public static Specification<Interview> hasAssociateEmail(String associateEmail) {
	    return (interview, cq, cb) -> cb.like(interview.get("associateEmail"), associateEmail);
	}

	public static Specification<Interview> hasManagerEmail(String managerEmail) {
	    return (interview, cq, cb) -> cb.like(interview.get("managerEmail"), managerEmail);
	}

	public static Specification<Interview> hasPlace(String place) {
	    return (interview, cq, cb) -> cb.like(interview.get("place"), place);
	}

	public static Specification<Interview> hasClient(String clientName) {
	    return (interview, cq, cb) -> cb.like(interview.join("client").get("clientName"), clientName);
	}
}