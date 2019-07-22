package com.revature.exceptionhandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.revature.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ServiceExceptionHandler {
	
	@ExceptionHandler(value = {ResourceNotFoundException.class})
	public ResponseEntity<Object> interviewNotFound(ResourceNotFoundException e) {
		
		System.out.println(e.getMessage());
		return new ResponseEntity<>(e.getStatus());
	}
	
	@ExceptionHandler(value = {Throwable.class}) 
	public ResponseEntity<Object> catchAll(Throwable e) {
		
		System.out.println(e);
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
