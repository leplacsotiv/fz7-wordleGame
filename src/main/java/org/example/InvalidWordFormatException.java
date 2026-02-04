package org.example;

public class InvalidWordFormatException extends WordleGameException {
    public InvalidWordFormatException(String message) {
        super("Некорректный ввод: " + message);
    }
}
