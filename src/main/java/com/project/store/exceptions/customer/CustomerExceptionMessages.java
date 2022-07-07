package com.project.store.exceptions.customer;

import org.springframework.http.HttpStatus;

public enum CustomerExceptionMessages {

    CUSTOMER_NOT_FOUND("Customer not found!", HttpStatus.NOT_FOUND.toString());

    private final String errorMessage;
    private final String httpStatusCode;

    CustomerExceptionMessages(String errorMessage, String httpStatusCode) {
        this.errorMessage = errorMessage;
        this.httpStatusCode = httpStatusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public String getHttpStatusCode() {
        return httpStatusCode;
    }
}
