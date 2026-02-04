package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {
    private final PrintWriter log;

    public WordleDictionaryLoader(PrintWriter log) {
        this.log = log;
    }

    public WordleDictionary load(Path file) throws DictionaryLoadException {
        List<String> words = new ArrayList<>();

        log.println("[DICT] Loading dictionary from " + file.toAbsolutePath());

        try (BufferedReader br = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String line;
//            while ((line = br.readLine()) != null) {
//                words.add(line);
//            }
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\s+");

                for (String p : parts) {
                    if (!p.isBlank()) {
                        words.add(p);
                    }
                }
            }

        } catch (IOException e) {
            throw new DictionaryLoadException("Cannot read dictionary file: " + file, e);
        }

        WordleDictionary dict = new WordleDictionary(words, log);
        if (dict.size() == 0) {
            throw new DictionaryLoadException("Dictionary has no valid 5-letter words after normalization.");
        }

        log.println("[DICT] Loaded valid words: " + dict.size());
        return dict;
    }
}
