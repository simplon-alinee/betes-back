package com.example.betes.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class ResourceAlreadyExistException extends RuntimeException {
	public ResourceAlreadyExistException()
	{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "match inexistant");
	}

	public ResourceAlreadyExistException(String message)
	{ throw new ResponseStatusException(HttpStatus.NOT_FOUND, message);
	}
}
