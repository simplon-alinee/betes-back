package com.example.betes.exception;

public class ResourceAlreadyExistException extends RuntimeException {
	public ResourceAlreadyExistException()
	{
	}

	public ResourceAlreadyExistException(String message)
	{
		super(message);
	}
}
