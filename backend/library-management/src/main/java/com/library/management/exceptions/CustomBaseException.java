package com.library.management.exceptions;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class CustomBaseException extends RuntimeException {

    private HttpStatus status;
    private String error;
    private String message;

}