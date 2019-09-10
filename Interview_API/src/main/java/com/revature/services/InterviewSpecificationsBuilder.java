package com.revature.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;

import com.revature.models.Interview;
import com.revature.utils.SearchCriteria;

public class InterviewSpecificationsBuilder {
	
	private final List<SearchCriteria> params;
	 
    public InterviewSpecificationsBuilder() {
        params = new ArrayList<SearchCriteria>();
    }
 
    public InterviewSpecificationsBuilder with(String key, String operation, Object value) {
        params.add(new SearchCriteria(key, operation, value));
        return this;
    }
 
    public Specification<Interview> build() {
        if (params.size() == 0) {
            return null;
        }
 
        List<Specification<Interview>> specs = params.stream()
          .map(InterviewSpecification::new)
          .collect(Collectors.toList());
         
        Specification<Interview> result = specs.get(0);
 
        for (int i = 1; i < params.size(); i++) {
            result = Specification.where(result).and(specs.get(i));
        }       
        return result;
    }

}
