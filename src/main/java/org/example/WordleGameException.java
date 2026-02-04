package org.example;

public class WordleGameException extends Exception {
    protected WordleGameException(String message) {
        super(message);
    }
    protected WordleGameException(String message, Throwable cause) {
        super(message, cause);
    }
}
