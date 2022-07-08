package com.project.store.exceptions.category;

import com.project.store.exceptions.server.BaseException;

public class ProductCategoryNotFoundException extends BaseException {

    public ProductCategoryNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }

    public ProductCategoryNotFoundException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}
