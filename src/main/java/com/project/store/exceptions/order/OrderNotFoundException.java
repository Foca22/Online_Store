package com.project.store.exceptions.order;

import com.project.store.exceptions.server.BaseException;

public class OrderNotFoundException extends BaseException {

    public OrderNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }

    public OrderNotFoundException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}
