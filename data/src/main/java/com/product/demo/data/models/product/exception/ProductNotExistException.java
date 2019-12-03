package com.product.demo.data.models.product.exception;

import com.product.demo.data.exception.ApiException;

import javax.ws.rs.core.Response;

public class ProductNotExistException extends ApiException {

    public ProductNotExistException(Response.Status status, String message) {
        super(status, message);
    }

    public ProductNotExistException(Response.Status status, String message, String errorCode) {
        super(status, message, errorCode);
    }

    public ProductNotExistException(Response.Status status, Throwable cause) {
        super(status, cause);
    }

    public ProductNotExistException(Response.Status status, String message, String description, String errorCode) {
        super(status, message, description, errorCode);
    }
}
