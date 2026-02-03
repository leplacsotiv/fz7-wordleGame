package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintWriter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleGameTest {

    private PrintWriter log;
    private WordleDictionary dict;
    private WordleGame game;

    @BeforeEach
    void setUp() {
        log = new PrintWriter(System.out, true);
        dict = new WordleDictionary(List.of("мотор"), log);
        game = new WordleGame(dict, log);
    }

    @Test
    void makeGuess_invalidFormat_throwsGameException() {
        assertThrows(InvalidWordFormatException.class, () -> game.makeGuess("мото"));
        assertThrows(InvalidWordFormatException.class, () -> game.makeGuess("motor"));
        assertThrows(InvalidWordFormatException.class, () -> game.makeGuess("мото1"));
    }

    @Test
    void makeGuess_notInDictionary_throwsGameException() {
        assertThrows(WordNotInDictionaryException.class, () -> game.makeGuess("потер"));
    }

    @Test
    void attemptsDecrease_onlyOnValidGuess() throws Exception {
        int before = game.attemptsLeft();

        try {
            game.makeGuess("мото");
        } catch (WordleGameException ignored) {}

        assertEquals(before, game.attemptsLeft());
        GuessResult r = game.makeGuess("мотор");
        assertEquals("+++++", r.hint());
        assertEquals(before - 1, game.attemptsLeft());
    }

    @Test
    void win_finishesGame() throws Exception {
        assertFalse(game.isFinished());
        game.makeGuess("мотор");
        assertTrue(game.isWon());
        assertTrue(game.isFinished());
    }

    @Test
    void suggest_returnsCandidate_andDoesNotRepeat() {
        String s1 = game.suggest();
        String s2 = game.suggest();

        assertNotNull(s1);
        assertEquals("мотор", s1);
        assertNull(s2);
    }
}
