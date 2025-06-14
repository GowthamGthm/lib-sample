package com.library.management.exceptions;

import org.springframework.http.HttpStatus;

public class UserOperationFailed  extends CustomBaseException {

    public UserOperationFailed(HttpStatus status, String error, String message) {
        super(status, error, message);
    }

}