package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordleHintTest {

    @Test
    void allCorrect_isAllPlus() {
        assertEquals("+++++", WordleHint.hint("мотор", "мотор"));
    }

    @Test
    void specExample_like() {
        assertEquals("-++-+", WordleHint.hint("мотор", "потер"));
    }

    @Test
    void noLettersMatch_isAllMinus() {
        assertEquals("-----", WordleHint.hint("мотор", "плеск"));
    }

    @Test
    void duplicates_areHandled() {
        assertEquals("^-+-+", WordleHint.hint("касса", "сосна"));
    }

    @Test
    void invalidLength_throws() {
        assertThrows(IllegalArgumentException.class, () -> WordleHint.hint("мотор", "мото"));
        assertThrows(IllegalArgumentException.class, () -> WordleHint.hint("мото", "мотор"));
    }
}
