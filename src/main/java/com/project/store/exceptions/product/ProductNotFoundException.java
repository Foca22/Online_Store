package com.project.store.exceptions.product;

import com.project.store.exceptions.server.BaseException;

public class ProductNotFoundException extends BaseException {

    public ProductNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ProductNotFoundException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}
