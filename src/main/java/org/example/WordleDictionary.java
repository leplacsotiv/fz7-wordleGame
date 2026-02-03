package org.example;

import java.io.PrintWriter;
import java.util.*;

public class WordleDictionary {
    private final List<String> words;
    private final Set<String> wordSet;
    private final Random rnd = new Random();
    private final PrintWriter log;

    public WordleDictionary(List<String> rawLines, PrintWriter log) {
        this.log = log;
        List<String> temp = new ArrayList<>();
        Set<String> set = new HashSet<>();

        for (String s : rawLines) {
            String w = normalize(s);
            if (!isValidFormat(w)) continue;
            if (set.add(w)) {
                temp.add(w);
            }
        }

        this.words = Collections.unmodifiableList(temp);
        this.wordSet = Collections.unmodifiableSet(set);

        log.println("[DICT] After cleanup/normalize unique words: " + words.size());
    }

    public int size() {
        return words.size();
    }

    public String randomWord() {
        return words.get(rnd.nextInt(words.size()));
    }

    public boolean contains(String raw) {
        return wordSet.contains(normalize(raw));
    }

    public List<String> allWords() {
        return words;
    }

    public static String normalize(String s) {
        if (s == null) {
            return "";
        }
        String x = s.trim().toLowerCase(Locale.ROOT);
        x = x.replace('ё', 'е');
        return x;
    }

    public static boolean isValidFormat(String word) {
        if (word.length() != 5) return false;
        for (int i = 0; i < 5; i++) {
            char ch = word.charAt(i);
            if (!(ch >= 'а' && ch <= 'я')) {
                return false;
            }
        }
        return true;
    }
}
