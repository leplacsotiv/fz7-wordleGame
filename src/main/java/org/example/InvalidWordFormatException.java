package org.example;

public class InvalidWordFormatException extends WordleGameException {
    public InvalidWordFormatException(String message) {
        super(message);
    }
    @Override public String getUserMessage() {
        return "Некорректный ввод: " + getMessage();
    }
}
