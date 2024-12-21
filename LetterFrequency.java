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
        StringBuilder textInputBuilder = new StringBuilder();
        
        System.out.println("Enter or paste your text (press Enter twice to finish):");
        String currentInputLine;
        String previousInputLine = null;
        
        while (textInputScanner.hasNextLine()) {
            currentInputLine = textInputScanner.nextLine();
            
            // Check for two consecutive empty lines to end input
            if (currentInputLine.trim().isEmpty() && previousInputLine != null && previousInputLine.trim().isEmpty()) {
                break;
            }
            
            textInputBuilder.append(currentInputLine).append("\n");
            previousInputLine = currentInputLine;
        }
        
        // Convert text to lowercase to count letters regardless of case
        String normalizedText = textInputBuilder.toString().toLowerCase();
        
        // Validate the input text
        TextValidator validator = new TextValidator(normalizedText);
        TextValidator.ValidationResult validationResult = validator.validate();
        
        if (!validationResult.isValid()) {
            System.out.println(validationResult.getMessage());
            textInputScanner.close();
            return;
        }
        
        // Create a map to store letter frequencies
        Map<Character, Integer> letterFrequencyMap = new HashMap<>();
        
        // Count frequencies, total letters and total characters
        int totalLetters = 0;
        int totalCharacters = 0;
        for (char currentChar : normalizedText.toCharArray()) {
            if (Character.isLetter(currentChar)) {
                letterFrequencyMap.put(currentChar, letterFrequencyMap.getOrDefault(currentChar, 0) + 1);
                totalLetters++;
            }
            if (currentChar != '\n') {  // Don't count newline characters
                totalCharacters++;
            }
        }
        
        // Count words
        String[] words = normalizedText.split("\\s+");
        int wordCount = 0;
        for (String word : words) {
            if (!word.trim().isEmpty()) {
                wordCount++;
            }
        }
        
        // Display summary
        System.out.println("\nText Analysis Summary:");
        System.out.println("Total words: " + wordCount);
        System.out.println("Total letters: " + totalLetters);
        System.out.println("Total characters (including spaces, punctuation): " + totalCharacters);
        
        // Calculate frequencies and percentages
        Map<Character, Double> letterFrequencyPercentages = LanguageFrequencyAnalyzer.calculatePercentages(letterFrequencyMap, totalLetters);
        
        // Display letter frequencies table with all languages
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
                
                // Print standard frequencies for each language
                for (String lang : SUPPORTED_LANGUAGES) {
                    double standardFreq = LanguageFrequencyAnalyzer.getStandardFrequency(letter, lang.toUpperCase());
                    System.out.printf(" %11.3f%% |", standardFreq);
                }
                System.out.println();
            });
            
        System.out.println("+---------+----------+-----------+---------------+---------------+---------------+---------------+---------------+");
        
        // Calculate differences for each language
        Map<String, Double> totalDifferences = new HashMap<>();
        for (String lang : SUPPORTED_LANGUAGES) {
            Map<Character, Double> differences = LanguageFrequencyAnalyzer.compareWithLanguage(letterFrequencyPercentages, lang.toUpperCase());
            double totalDiff = differences.values().stream()
                .map(Math::abs)
                .mapToDouble(Double::doubleValue)
                .sum();
            totalDifferences.put(lang, totalDiff);
        }

        // Find the closest matching language
        String closestLanguage = totalDifferences.entrySet().stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse("English");

        // Display language analysis
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
        
        textInputScanner.close();
    }
}
