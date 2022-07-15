package com.project.store.exceptions.category;

import com.project.store.exceptions.server.BaseException;

public class CategoryNotFoundException extends BaseException {

    public CategoryNotFoundException(String message, String errorCode) {
        super(message, errorCode);
    }

    public CategoryNotFoundException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}
