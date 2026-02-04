package org.example;

import java.util.*;

public final class CandidateSet {
    private final List<String> words;

    public CandidateSet(List<String> words) {
        this.words = List.copyOf(words);
    }

    public int size() {
        return words.size();
    }

    public List<String> asList() {
        return words;
    }

    public CandidateSet filtered(Constraints constraints) {
        List<String> out = new ArrayList<>();
        for (String w : words) {
            if (constraints.matches(w)) out.add(w);
        }
        return new CandidateSet(out);
    }

    public String random(Random rnd) {
        if (words.isEmpty()) return null;
        return words.get(rnd.nextInt(words.size()));
    }
}
