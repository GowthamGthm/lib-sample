package com.library.management.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;


public class BookOperationFailed extends CustomBaseException {

    public BookOperationFailed(HttpStatus status, String error, String message) {
        super(status, error, message);
    }

}