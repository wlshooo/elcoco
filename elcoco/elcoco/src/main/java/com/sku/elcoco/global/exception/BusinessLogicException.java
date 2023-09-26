package com.sku.elcoco.global.exception;


import com.sku.elcoco.global.model.ResponseStatus;

public class BusinessLogicException extends RuntimeException {

    private ResponseStatus responseStatus;

    public BusinessLogicException(ResponseStatus responseStatus) {
        super(responseStatus.getMessage());
        this.responseStatus = responseStatus;
    }

    public BusinessLogicException(String message) {
        super(message);
    }
}
