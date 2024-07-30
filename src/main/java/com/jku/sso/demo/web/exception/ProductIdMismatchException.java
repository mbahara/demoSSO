package com.jku.sso.demo.web.exception;

public class ProductIdMismatchException extends RuntimeException {
    public ProductIdMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
    public ProductIdMismatchException() { }
}
