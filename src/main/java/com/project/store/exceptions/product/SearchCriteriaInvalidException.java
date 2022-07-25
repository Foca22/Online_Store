package com.project.store.exceptions.product;

import com.project.store.exceptions.server.BaseException;

public class SearchCriteriaInvalidException extends BaseException {
    public SearchCriteriaInvalidException(String message, String errorCode) {
        super(message, errorCode);
    }

    public SearchCriteriaInvalidException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}
