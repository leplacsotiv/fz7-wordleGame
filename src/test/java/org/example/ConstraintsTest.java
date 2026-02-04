package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstraintsTest {

    @Test
    void fixedPositions_fromPlus_areHonored() {
        Constraints c = new Constraints();

        String answer = "мотор";
        String guess = "потер";
        String hint = WordleHint.hint(answer, guess);

        c.absorbGuess(guess, hint);

        assertTrue(c.matches("мотор"));
        assertFalse(c.matches("матор"));
        assertFalse(c.matches("модор"));
        assertFalse(c.matches("моток"));
    }

    @Test
    void forbiddenPositions_fromCaret_areHonored() {
        Constraints c = new Constraints();

        String answer = "мотор";
        String guess = "роман";
        String hint = WordleHint.hint(answer, guess);

        c.absorbGuess(guess, hint);

        assertFalse(c.matches("роман"));
        assertTrue(c.matches("мотор"));
    }

    @Test
    void minCount_isRespected() {
        Constraints c = new Constraints();

        String hint = WordleHint.hint("касса", "масса");
        c.absorbGuess("масса", hint);

        assertTrue(c.matches("касса"));
        assertFalse(c.matches("каска"));
    }

    @Test
    void maxCount_whenHitAndMiss_inSameGuess() {
        Constraints c = new Constraints();

        String answer = "касса";
        String guess  = "сссак";
        String hint = WordleHint.hint(answer, guess);

        c.absorbGuess(guess, hint);

        assertTrue(c.matches("касса"));
        assertFalse(c.matches("ссска"));
    }

    @Test
    void onlyMinus_meansLetterAbsent() {
        Constraints c = new Constraints();

        String hint = WordleHint.hint("мотор", "плеск");
        c.absorbGuess("плеск", hint);

        assertFalse(c.matches("порох"));
        assertTrue(c.matches("мотор"));
    }
}
