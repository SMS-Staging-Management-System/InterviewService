package com.revature.services;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.revature.models.Client;
import com.revature.models.Interview;
import com.revature.utils.SearchCriteria;


public class InterviewSpecification implements Specification<Interview> {
	
	 private SearchCriteria criteria;
	 
		public SearchCriteria getCriteria() {
			return criteria;
		}

		public void setCriteria(SearchCriteria criteria) {
			this.criteria = criteria;
		}

		public InterviewSpecification(SearchCriteria criteria) {
			super();
			this.criteria = criteria;
		}

		public InterviewSpecification() {
			super();
			// TODO Auto-generated constructor stub
		}
		
		
	    @Override
	    public Predicate toPredicate
	      (Root<Interview> root, CriteriaQuery<?> query, CriteriaBuilder builder) {
	  
	    	//Predicate checks for operator
	        if (criteria.getOperation().equalsIgnoreCase(":")) {
	        	
	        	//Predicate checks if key is of String type
	            if (root.get(criteria.getKey()).getJavaType() == String.class) {
	            	
	            	//get key and value for filtering on root(Interview table)
	                return builder.like(
	                  root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
	                
	                //Predicate checks for client object
	            } else if (root.get(criteria.getKey()).getJavaType() == Client.class){
	            	
	            	//Joins on client column
	            	Join<Interview, Client> clientJoin = root.join("client");
	                
	            	//Join allows us to filter client fields from Interview.client; see clientJoin below replaces root
	            	 return builder.like(
	            			 clientJoin.get("clientName"), "%" + criteria.getValue() + "%");
	            }
	           
	            else {
	                return builder.equal(root.get(criteria.getKey()), criteria.getValue());
	            }
	        }
	        return null;
	    }

	    
}
