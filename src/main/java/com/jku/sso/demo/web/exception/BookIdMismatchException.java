package com.jku.sso.demo.web.exception;

public class BookIdMismatchException extends RuntimeException {
    public BookIdMismatchException(String message, Throwable cause) {
        super(message, cause);
    }
    public BookIdMismatchException() { }
}
