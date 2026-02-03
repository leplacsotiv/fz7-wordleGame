package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WordleDictionaryLoaderTest {

    private final PrintWriter log = new PrintWriter(System.out, true);

    @TempDir
    Path tempDir;

    @Test
    void load_readsFile_andBuildsDictionary() throws Exception {
        Path file = tempDir.resolve("dictionary.txt");
        Files.write(file, List.of(
                " МОТОР ",
                "мотор",
                "мото",
                "ёлкаа",
                "abcde"
        ), StandardCharsets.UTF_8);

        WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
        WordleDictionary dict = loader.load(file);

        assertTrue(dict.contains("мотор"));
        assertTrue(dict.contains("елкаа"));
        assertFalse(dict.contains("abcde"));
        assertFalse(dict.contains("мото"));
        assertEquals(2, dict.size());
    }

    @Test
    void load_missingFile_throwsDictionaryLoadException() {
        Path missing = tempDir.resolve("missing.txt");

        WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
        assertThrows(DictionaryLoadException.class, () -> loader.load(missing));
    }

    @Test
    void load_fileWithNoValidWords_throwsDictionaryLoadException() throws Exception {
        Path file = tempDir.resolve("dictionary.txt");
        Files.write(file, List.of(
                "мото",
                "abcd",
                "12345",
                "длиннее"
        ), StandardCharsets.UTF_8);

        WordleDictionaryLoader loader = new WordleDictionaryLoader(log);

        assertThrows(DictionaryLoadException.class, () -> loader.load(file));
    }
}
