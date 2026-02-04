package org.example;

public class WordNotInDictionaryException extends WordleGameException {
    public WordNotInDictionaryException(String word) {
        super("Слова нет в словаре: " + word);
    }
}

