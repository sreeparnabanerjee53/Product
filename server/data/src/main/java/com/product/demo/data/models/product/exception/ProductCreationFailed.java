package com.product.demo.data.models.product.exception;

import com.product.demo.data.exception.ApiException;

import javax.ws.rs.core.Response;

public class ProductCreationFailed extends ApiException {

    public ProductCreationFailed(Response.Status status, String message) {
        super(status, message);
    }

    public ProductCreationFailed(Response.Status status, String message, String errorCode) {
        super(status, message, errorCode);
    }

    public ProductCreationFailed(Response.Status status, Throwable cause) {
        super(status, cause);
    }

    public ProductCreationFailed(Response.Status status, String message, String description, String errorCode) {
        super(status, message, description, errorCode);
    }
}
