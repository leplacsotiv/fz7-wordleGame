package org.example;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Scanner;

public class Wordle {

    public static void main(String[] args) {
        try (PrintWriter log = new PrintWriter(new OutputStreamWriter(
                new FileOutputStream("wordle.log", true), StandardCharsets.UTF_8), true)) {

            Path dictPath = Path.of("src/main/resources/" +
                    "dictionary.txt");
            WordleDictionaryLoader loader = new WordleDictionaryLoader(log);
            WordleDictionary dict = loader.load(dictPath);

            WordleGame game = new WordleGame(dict, log);

            Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);
            System.out.println("Wordle (RU) — угадайте слово из 5 букв. Попыток: 6");
            System.out.println("Подсказка: '+' — буква и позиция верны, '^' — буква есть, но позиция другая, '-' — буквы нет (или лишняя копия).");
            System.out.println("Нажмите Enter на пустой строке — получите подсказку-слово.");

            while (!game.isFinished()) {
                System.out.print("> ");
                String raw = sc.nextLine();

                if (raw == null) {
                    raw = "";
                }
                raw = raw.trim();

                if (raw.isEmpty()) {
                    String suggestion = game.suggest();
                    if (suggestion == null) {
                        System.out.println("(нет доступных подсказок)");
                    } else {
                        System.out.println("Подсказка: " + suggestion);
                    }
                    continue;
                }

                try {
                    GuessResult res = game.makeGuess(raw);
                    System.out.println(res.normalizedGuess());
                    System.out.println(res.hint());
                    System.out.println("Осталось попыток: " + game.attemptsLeft());

                } catch (WordleGameException e) {
                    System.out.println(e.getMessage());
                    log.println("[GAME] " + e.getMessage());
                }
            }

            if (game.isWon()) {
                System.out.println("Вы выиграли!");
            } else {
                System.out.println("Вы проиграли.");
            }
            System.out.println("Загаданное слово: " + game.answer());

        } catch (Exception e) {
            System.out.println("Произошла системная ошибка. Подробности записаны в wordle.log");
            try (PrintWriter fallback = new PrintWriter(new OutputStreamWriter(
                    new FileOutputStream("wordle.log", true), StandardCharsets.UTF_8), true)) {
                fallback.println("[FATAL] " + e);
                e.printStackTrace(fallback);
            } catch (IOException ignored) {
            }
        }
    }
}
