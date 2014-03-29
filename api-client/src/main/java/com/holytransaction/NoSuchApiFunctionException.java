package com.holytransaction;

public class NoSuchApiFunctionException extends Exception {
    public NoSuchApiFunctionException(String message) {
        super(message);
    }

    public NoSuchApiFunctionException() {
    }
}
