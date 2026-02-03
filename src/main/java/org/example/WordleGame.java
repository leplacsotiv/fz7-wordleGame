package org.example;

import java.io.PrintWriter;
import java.util.*;

public class WordleGame {
    private final WordleDictionary dict;
    private final PrintWriter log;

    private final String answer;
    private int attemptsLeft = 6;
    private boolean won = false;

    private final Constraints constraints = new Constraints();
    private List<String> candidates;
    private final Set<String> suggestedAlready = new HashSet<>();

    public WordleGame(WordleDictionary dict, PrintWriter log) {
        this.dict = dict;
        this.log = log;
        this.answer = dict.randomWord();
        this.candidates = new ArrayList<>(dict.allWords());

        log.println("[GAME] Started. answer=" + answer + " candidates=" + candidates.size());
    }

    public GuessResult makeGuess(String rawInput) throws WordleGameException {
        if (isFinished()) {
            throw new IllegalStateException("Game already finished");
        }

        String guess = WordleDictionary.normalize(rawInput);

        if (!WordleDictionary.isValidFormat(guess)) {
            throw new InvalidWordFormatException("Нужно слово из 5 русских букв.");
        }

        if (!dict.contains(guess)) {
            throw new WordNotInDictionaryException("Слова нет в словаре, попробуйте другое.");
        }

        String hint = WordleHint.hint(answer, guess);

        constraints.absorbGuess(guess, hint);

        candidates = CandidateFilter.filter(candidates, constraints);

        attemptsLeft--;

        if (guess.equals(answer)) {
            won = true;
        }

        log.println("[GAME] guess=" + guess + " hint=" + hint +
                " attemptsLeft=" + attemptsLeft + " candidates=" + candidates.size());

        if (!won && attemptsLeft > 0 && candidates.isEmpty()) {
            throw new IllegalStateException("No candidates left - constraint logic bug.");
        }

        return new GuessResult(guess, hint);
    }

    public String suggest() {
        if (isFinished()) {
            return null;
        }

        for (int tries = 0; tries < 50; tries++) {
            if (candidates.isEmpty()) {
                return null;
            }
            String w = candidates.get(new Random().nextInt(candidates.size()));
            if (suggestedAlready.add(w)) {
                log.println("[GAME] suggested=" + w);
                return w;
            }
        }

        for (String w : candidates) {
            if (suggestedAlready.add(w)) {
                log.println("[GAME] suggested=" + w);
                return w;
            }
        }
        return null;
    }

    public boolean isFinished() {
        return won || attemptsLeft <= 0;
    }

    public boolean isWon() {
        return won;
    }

    public int attemptsLeft() {
        return attemptsLeft;
    }

    public String answer() {
        return answer;
    }
}
