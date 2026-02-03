package org.example;

public class WordNotInDictionaryException extends WordleGameException {
    public WordNotInDictionaryException(String message) {
        super(message);
    }
    @Override public String getUserMessage() {
        return getMessage();
    }
}

