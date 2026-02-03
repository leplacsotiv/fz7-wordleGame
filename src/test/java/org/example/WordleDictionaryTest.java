package org.example;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryTest {

    private final PrintWriter log = new PrintWriter(System.out, true);

    @Test
    void normalize_lowercase_trim_and_yo_to_e() {
        assertEquals("мотор", WordleDictionary.normalize(" МОТОР "));
        assertEquals("елкаа", WordleDictionary.normalize("ЁлкАа"));
        assertEquals("", WordleDictionary.normalize(null));
    }

    @Test
    void isValidFormat_acceptsOnlyFiveRussianLetters() {
        assertTrue(WordleDictionary.isValidFormat("мотор"));
        assertFalse(WordleDictionary.isValidFormat("мото"));
        assertFalse(WordleDictionary.isValidFormat("моторрр"));
        assertFalse(WordleDictionary.isValidFormat("motor"));
        assertFalse(WordleDictionary.isValidFormat("мото1"));
        assertFalse(WordleDictionary.isValidFormat("мо-ор"));
    }

    @Test
    void constructor_filters_and_deduplicates_words() {
        WordleDictionary dict = new WordleDictionary(List.of(
                " МОТОР ",
                "мотор",
                "мото",
                "motor",
                "ёлкаа"
        ), log);

        assertTrue(dict.contains("мотор"));
        assertTrue(dict.contains(" МОТОР "));
        assertTrue(dict.contains("Елкаа"));

        assertFalse(dict.contains("мото"));
        assertFalse(dict.contains("motor"));

        assertEquals(2, dict.size());
    }

    @Test
    void randomWord_returnsElementFromDictionary() {
        WordleDictionary dict = new WordleDictionary(List.of("мотор"), log);
        assertEquals("мотор", dict.randomWord());
    }
}
