package com.jku.sso.demo.web.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public ProductNotFoundException() { }

}
