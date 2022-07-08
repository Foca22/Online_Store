package com.project.store.exceptions.category;

import org.springframework.http.HttpStatus;

public enum ProductCategoryExceptionMessages {

    PRODUCT_CATEGORY_NOT_FOUND("Product Category not found!", HttpStatus.NOT_FOUND.toString());

    private final String errorMessage;
    private final String httpStatusCode;

    ProductCategoryExceptionMessages(String errorMessage, String httpStatusCode) {
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
