package com.project.store.exceptions.id;

import com.project.store.exceptions.server.BaseException;

public class IdNullException extends BaseException {
    public IdNullException(String message, String errorCode) {
        super(message, errorCode);
    }

    public IdNullException(String message, Throwable cause, String errorCode) {
        super(message, cause, errorCode);
    }
}
