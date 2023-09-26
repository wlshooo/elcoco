package com.sku.elcoco.global.exception;


import com.sku.elcoco.global.model.ResponseStatus;

public class WrongPasswordException extends BusinessLogicException {

    public WrongPasswordException(ResponseStatus responseStatus) {
        super(responseStatus);
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}