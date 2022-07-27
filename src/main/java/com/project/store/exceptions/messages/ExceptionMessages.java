package com.project.store.exceptions.messages;

import org.springframework.http.HttpStatus;

public enum ExceptionMessages {

    NULL_ID("No ID specified!", HttpStatus.BAD_REQUEST.toString()),
    CUSTOMER_NOT_FOUND("Customer not found!", HttpStatus.NOT_FOUND.toString()),
    PRODUCT_CATEGORY_NOT_FOUND("Product Category not found!", HttpStatus.NOT_FOUND.toString()),
    PRODUCT_NOT_FOUND("Product not found!", HttpStatus.NOT_FOUND.toString()),
    INVALID_SEARCH_CRITERIA("Invalid search criteria!", HttpStatus.BAD_REQUEST.toString());

    private final String errorMessage;
    private final String httpStatusCode;

    ExceptionMessages(String errorMessage, String httpStatusCode) {
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
