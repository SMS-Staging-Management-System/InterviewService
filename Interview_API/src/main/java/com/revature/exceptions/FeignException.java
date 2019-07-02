package com.revature.exceptions;

import org.springframework.http.HttpStatus;

public class FeignException extends AbstractServiceException {

	private static final long serialVersionUID = 1L;
	
	public FeignException(int status, String reason) {
		
		super(HttpStatus.resolve(status), reason);
	}

	@Override
	public String toString() {
		return "FeignClientException [status=" + this.getStatus() + ", reason=" + this.getMessage() + "]";
	}

}
