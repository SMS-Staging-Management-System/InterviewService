package com.revature.repos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.revature.models.*;

public interface InterviewRepo extends JpaRepository<Interview, Integer> {
	
	List<Interview> findByAssociateEmail(String email);

	Optional<Interview> save(Optional<Interview> i);

}
