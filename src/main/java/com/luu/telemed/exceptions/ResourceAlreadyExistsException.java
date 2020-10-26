package com.luu.telemed.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 
 * @author HungLQ7130
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceAlreadyExistsException extends Exception {

	private static final long serialVersionUID = 1L;

	public ResourceAlreadyExistsException(String message) {
		super(message);
	}

}
