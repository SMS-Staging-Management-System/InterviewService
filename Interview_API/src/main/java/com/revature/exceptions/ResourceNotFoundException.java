package com.revature.exceptions;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends AbstractServiceException {

	private static final long serialVersionUID = 1L;

	public static HttpStatus status = HttpStatus.NOT_FOUND;
	
	public ResourceNotFoundException(String message) {
		super(status, message);
	}
}
