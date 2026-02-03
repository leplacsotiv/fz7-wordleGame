package org.example;

public abstract class WordleGameException extends Exception {
    protected WordleGameException(String message) {
        super(message);
    }
    public abstract String getUserMessage();
}
