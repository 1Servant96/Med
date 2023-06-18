package com.medcheck.db.service.serviceimpl;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailAlreadyExistException extends RuntimeException {
    public EmailAlreadyExistException() {
        super();
    }

    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
