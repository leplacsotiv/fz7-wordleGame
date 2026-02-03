package org.example;

import java.util.HashMap;
import java.util.Map;

public final class WordleHint {
    private WordleHint() {}

    public static String hint(String answer, String guess) {
        if (answer.length() != 5 || guess.length() != 5) {
            throw new IllegalArgumentException("answer/guess must be length=5");
        }

        char[] res = new char[5];
        Map<Character, Integer> remaining = new HashMap<>();

        for (int i = 0; i < 5; i++) {
            char a = answer.charAt(i);
            char g = guess.charAt(i);
            if (g == a) {
                res[i] = '+';
            } else {
                remaining.merge(a, 1, Integer::sum);
            }
        }

        for (int i = 0; i < 5; i++) {
            if (res[i] == '+') continue;

            char g = guess.charAt(i);
            int cnt = remaining.getOrDefault(g, 0);
            if (cnt > 0) {
                res[i] = '^';
                if (cnt == 1) remaining.remove(g);
                else remaining.put(g, cnt - 1);
            } else {
                res[i] = '-';
            }
        }

        return new String(res);
    }
}
