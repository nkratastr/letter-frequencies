import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class LetterFrequency {
    private static final List<String> SUPPORTED_LANGUAGES = List.of(
        "English",
        "French",
        "German",
        "Italian",
        "Dutch"
    );

    public static void main(String[] args) {
        Scanner textInputScanner = new Scanner(System.in);
        String normalizedText = getUserInput(textInputScanner);
        
        // Validate the input text
        if (!validateText(normalizedText)) {
            textInputScanner.close();
            return;
        }

        // Process text and get statistics
        TextStatistics stats = processText(normalizedText);
        
        // Display results
        displaySummary(stats);
        displayLetterFrequencies(stats.letterFrequencyMap, stats.totalLetters);
        displayLanguageAnalysis(stats.letterFrequencyMap, stats.totalLetters);
        
        textInputScanner.close();
    }

    private static String getUserInput(Scanner scanner) {
        StringBuilder textInputBuilder = new StringBuilder();
        System.out.println("Enter or paste your text (press Enter twice to finish):");
        String currentInputLine;
        String previousInputLine = null;
        
        while (scanner.hasNextLine()) {
            currentInputLine = scanner.nextLine();
            
            if (currentInputLine.trim().isEmpty() && previousInputLine != null && previousInputLine.trim().isEmpty()) {
                break;
            }
            
            textInputBuilder.append(currentInputLine).append("\n");
            previousInputLine = currentInputLine;
        }
        
        return textInputBuilder.toString().toLowerCase();
    }

    private static boolean validateText(String text) {
        TextValidator validator = new TextValidator(text);
        TextValidator.ValidationResult validationResult = validator.validate();
        
        if (!validationResult.isValid()) {
            System.out.println(validationResult.getMessage());
            return false;
        }
        return true;
    }

    private static TextStatistics processText(String text) {
        Map<Character, Integer> letterFrequencyMap = new HashMap<>();
        int totalLetters = 0;
        int totalCharacters = 0;
        
        for (char currentChar : text.toCharArray()) {
            if (Character.isLetter(currentChar)) {
                letterFrequencyMap.put(currentChar, letterFrequencyMap.getOrDefault(currentChar, 0) + 1);
                totalLetters++;
            }
            if (currentChar != '\n') {
                totalCharacters++;
            }
        }
        
        String[] words = text.split("\\s+");
        int wordCount = 0;
        for (String word : words) {
            if (!word.trim().isEmpty()) {
                wordCount++;
            }
        }
        
        return new TextStatistics(letterFrequencyMap, totalLetters, totalCharacters, wordCount);
    }

    private static void displaySummary(TextStatistics stats) {
        System.out.println("\nText Analysis Summary:");
        System.out.println("Total words: " + stats.wordCount);
        System.out.println("Total letters: " + stats.totalLetters);
        System.out.println("Total characters (including spaces, punctuation): " + stats.totalCharacters);
    }

    private static void displayLetterFrequencies(Map<Character, Integer> letterFrequencyMap, int totalLetters) {
        Map<Character, Double> letterFrequencyPercentages = LanguageFrequencyAnalyzer.calculatePercentages(letterFrequencyMap, totalLetters);
        
        System.out.println("\nLetter Frequency Analysis:");
        System.out.println("+---------+----------+-----------+---------------+---------------+---------------+---------------+---------------+");
        System.out.println("| Letter  | Count    | Your Text | English Std % | French Std % | German Std % | Italian Std % | Dutch Std %   |");
        System.out.println("+---------+----------+-----------+---------------+---------------+---------------+---------------+---------------+");
        
        letterFrequencyMap.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(letterEntry -> {
                char letter = letterEntry.getKey();
                int count = letterEntry.getValue();
                double percentage = letterFrequencyPercentages.get(letter);
                
                System.out.printf("| %-7c | %8d | %9.3f%% |", letter, count, percentage);
                
                for (String lang : SUPPORTED_LANGUAGES) {
                    double standardFreq = LanguageFrequencyAnalyzer.getStandardFrequency(letter, lang.toUpperCase());
                    System.out.printf(" %11.3f%% |", standardFreq);
                }
                System.out.println();
            });
        
        System.out.println("+---------+----------+-----------+---------------+---------------+---------------+---------------+---------------+");
    }

    private static void displayLanguageAnalysis(Map<Character, Integer> letterFrequencyMap, int totalLetters) {
        Map<Character, Double> letterFrequencyPercentages = LanguageFrequencyAnalyzer.calculatePercentages(letterFrequencyMap, totalLetters);
        
        Map<String, Double> totalDifferences = new HashMap<>();
        for (String lang : SUPPORTED_LANGUAGES) {
            Map<Character, Double> differences = LanguageFrequencyAnalyzer.compareWithLanguage(letterFrequencyPercentages, lang.toUpperCase());
            double totalDiff = differences.values().stream()
                .map(Math::abs)
                .mapToDouble(Double::doubleValue)
                .sum();
            totalDifferences.put(lang, totalDiff);
        }

        String closestLanguage = totalDifferences.entrySet().stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("English");

        System.out.println("\nLanguage Analysis (lower difference means closer match):");
        System.out.println("+---------------+------------------+");
        System.out.println("| Language      | Total Difference |");
        System.out.println("+---------------+------------------+");
        
        totalDifferences.entrySet().stream()
            .sorted(Map.Entry.comparingByValue())
            .forEach(entry -> {
                String marker = entry.getKey().equals(closestLanguage) ? " (Best Match)" : "";
                System.out.printf("| %-13s | %14.3f%% |%s\n",
                    entry.getKey(), entry.getValue(), marker);
            });
            
        System.out.println("+---------------+------------------+");
    }

    private static class TextStatistics {
        final Map<Character, Integer> letterFrequencyMap;
        final int totalLetters;
        final int totalCharacters;
        final int wordCount;

        TextStatistics(Map<Character, Integer> letterFrequencyMap, int totalLetters, int totalCharacters, int wordCount) {
            this.letterFrequencyMap = letterFrequencyMap;
            this.totalLetters = totalLetters;
            this.totalCharacters = totalCharacters;
            this.wordCount = wordCount;
        }
    }
}
