package com.api.people.manager.exceptions;

public class ResourceNotFoundException extends Exception {
	private static final long serialVersionUID = -6248864892274230022L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}