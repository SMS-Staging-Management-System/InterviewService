package com.revature.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.revature.cognito.annotations.CognitoAuth;
import com.revature.cognito.constants.CognitoRoles;
import com.revature.models.User;

@FeignClient(name="user-service")
public interface IUserClient {

	//@CognitoAuth(roles = { "staging-manager" })
	@GetMapping
	String findAll();

	@GetMapping("users/{id}")
	public User findById(@PathVariable("id") int id);

	@CognitoAuth(roles = { "staging-manager" })
	@GetMapping(path = "users/email/{email:.+}")
	public ResponseEntity<User> findByEmail(@PathVariable String email);
	
	@CognitoAuth(roles = { "staging-manager" })
	@GetMapping(path = "users/user/email/{email}")
	public ResponseEntity<com.revature.feign.User> getByEmail(@PathVariable String email);
	
	@GetMapping("users/user/{email}")
	public com.revature.feign.User getUserByEmail(@PathVariable String email);

	@CognitoAuth(roles = { CognitoRoles.STAGING_MANAGER, CognitoRoles.TRAINER })
	@GetMapping("cohorts/{id}")
	public List<User> findAllByCohortId(@PathVariable("id") int id);

	@CognitoAuth(roles = { "staging-manager" })
	@PostMapping
	public User save(@RequestBody User u);
}
