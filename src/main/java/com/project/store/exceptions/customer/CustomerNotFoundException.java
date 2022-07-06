package com.project.store.exceptions.customer;

import com.project.store.exceptions.server.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class CustomerNotFoundException extends BaseException {

    public CustomerNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }

    public CustomerNotFoundException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}
