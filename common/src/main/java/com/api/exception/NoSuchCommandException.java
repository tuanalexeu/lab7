package com.api.exception;

public class NoSuchCommandException extends RuntimeException {
    public NoSuchCommandException(String msg) {
        super(msg);
    }
}
