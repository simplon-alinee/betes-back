package com.example.betes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends NoSuchElementException {

	public ResourceNotFoundException()
	{
	}

	public ResourceNotFoundException(String message)
	{
		super(message);
	}
    //
}
