package com.revature.exceptions;

import org.springframework.http.HttpStatus;

public abstract class AbstractServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	HttpStatus status;
	String message;
	
	public AbstractServiceException(HttpStatus hs) {
		
		this.status = hs;
	}
	
	public AbstractServiceException(HttpStatus hs, String message) {
		
		this.status = hs;
		this.message = message;
	}
	
	public HttpStatus getStatus() {
		return this.status;
	}
	
	public String getMessage() {
		return this.message;
	}
}
