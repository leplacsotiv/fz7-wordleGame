package org.example;

import java.util.*;

public final class Constraints {
    private final char[] fixed = new char[5];
    private final List<Set<Character>> forbiddenAtPos = List.of(
            new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>(), new HashSet<>()
    );

    private final Map<Character, Integer> minCount = new HashMap<>();
    private final Map<Character, Integer> maxCount = new HashMap<>();

    private boolean isFixedAt(int i) {
        return fixed[i] != '\0';
    }

    public void absorbGuess(String guess, String hint) {
        Map<Character, Integer> hits = new HashMap<>();
        Map<Character, Integer> misses = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            char g = guess.charAt(i);
            char h = hint.charAt(i);

            if (h == '+') {
                if (isFixedAt(i) && fixed[i] != g) {
                    throw new IllegalStateException("Contradicting fixed position at " + i);
                }
                fixed[i] = g;
                hits.merge(g, 1, Integer::sum);
            } else if (h == '^') {
                forbiddenAtPos.get(i).add(g);
                hits.merge(g, 1, Integer::sum);
            } else if (h == '-') {
                misses.merge(g, 1, Integer::sum);
            } else {
                throw new IllegalArgumentException("Unknown hint char: " + h);
            }
        }

        for (var e : hits.entrySet()) {
            char ch = e.getKey();
            int required = e.getValue();
            minCount.merge(ch, required, Math::max);
        }

        for (var e : misses.entrySet()) {
            char ch = e.getKey();
            int hit = hits.getOrDefault(ch, 0);

            if (hit == 0) {
                tightenMax(ch, 0);
            } else {
                tightenMax(ch, hit);
            }
        }

        for (var e : minCount.entrySet()) {
            char ch = e.getKey();
            int min = e.getValue();
            Integer max = maxCount.get(ch);
            if (max != null && max < min) {
                throw new IllegalStateException("maxCount < minCount for " + ch);
            }
        }
    }

    private void tightenMax(char ch, int newMax) {
        maxCount.merge(ch, newMax, Math::min);
    }

    public boolean matches(String word) {
        if (word.length() != 5) {
            return false;
        }

        for (int i = 0; i < 5; i++) {
            if (isFixedAt(i) && word.charAt(i) != fixed[i]) {
                return false;
            }
        }

        for (int i = 0; i < 5; i++) {
            if (forbiddenAtPos.get(i).contains(word.charAt(i))) {
                return false;
            }
        }

        Map<Character, Integer> cnt = new HashMap<>();
        for (int i = 0; i < 5; i++) {
            cnt.merge(word.charAt(i), 1, Integer::sum);
        }

        for (var e : minCount.entrySet()) {
            if (cnt.getOrDefault(e.getKey(), 0) < e.getValue()) {
                return false;
            }
        }

        for (var e : maxCount.entrySet()) {
            if (cnt.getOrDefault(e.getKey(), 0) > e.getValue()) {
                return false;
            }
        }

        return true;
    }
}
