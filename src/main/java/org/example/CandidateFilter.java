package org.example;

import java.util.ArrayList;
import java.util.List;

public final class CandidateFilter {
    private CandidateFilter() {

    }

    public static List<String> filter(List<String> from, Constraints c) {
        List<String> out = new ArrayList<>();
        for (String w : from) {
            if (c.matches(w)) {
                out.add(w);
            }
        }
        return out;
    }
}

