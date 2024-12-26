package com.letterfrequency.ui;

import com.letterfrequency.model.Language;
import com.letterfrequency.service.CombinedLanguageDetectionService;
import com.letterfrequency.service.FrequencyAnalysisService;
import com.letterfrequency.service.LanguageDetectionService;
import com.letterfrequency.service.LevenshteinAnalysisService;
import com.letterfrequency.util.TextValidator;
import java.util.EnumMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleUI {
    private final Scanner scanner;
    private final LanguageDetectionService languageService;
    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String YELLOW = "\u001B[33m";
    private static final String GREEN = "\u001B[32m";
    
    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.languageService = new CombinedLanguageDetectionService();
    }
    
    public void start() {
        System.out.println("Enter or paste your text (press Enter twice to finish):");
        String text = getUserInput();
        
        if (!validateText(text)) {
            return;
        }
        
        Map<Language, Double> scores = languageService.getLanguageScores(text);
        Language detectedLanguage = languageService.detectLanguage(text);
        
        displayResults(text, scores, detectedLanguage);
    }
    
    private String getUserInput() {
        StringBuilder textBuilder = new StringBuilder();
        String previousLine = null;
        String currentLine;
        
        while (scanner.hasNextLine()) {
            currentLine = scanner.nextLine();
            
            if (currentLine.trim().isEmpty() && previousLine != null && previousLine.trim().isEmpty()) {
                break;
            }
            
            textBuilder.append(currentLine).append("\n");
            previousLine = currentLine;
        }
        
        return textBuilder.toString().toLowerCase();
    }
    
    private boolean validateText(String text) {
        TextValidator validator = new TextValidator(text);
        TextValidator.ValidationResult result = validator.validate();
        
        if (!result.isValid()) {
            System.out.println(result.getMessage());
            return false;
        }
        return true;
    }
    
    private void displayResults(String text, Map<Language, Double> scores, Language bestMatch) {
        // Get individual scores
        FrequencyAnalysisService freqService = new FrequencyAnalysisService();
        LevenshteinAnalysisService levService = new LevenshteinAnalysisService();
        Map<Language, Double> freqScores = freqService.getLanguageScores(text);
        Map<Language, Double> levScores = levService.getLanguageScores(text);

        // Display language detection results
        System.out.println("\nLanguage Detection Results:");
        System.out.println("+---------------+-------------+-------------+-------------+");
        System.out.println("| Language      | Frequency  | Levenshtein | Combined   |");
        System.out.println("+---------------+-------------+-------------+-------------+");
        
        // Convert scores to percentages where higher is better
        Map<Language, Double> displayScores = new EnumMap<>(Language.class);
        scores.forEach((lang, score) -> {
            displayScores.put(lang, score * 100);
        });
        
        displayScores.entrySet().stream()
            .sorted(Map.Entry.<Language, Double>comparingByValue().reversed())
            .forEach(entry -> {
                Language lang = entry.getKey();
                String marker = lang == bestMatch ? " (Best Match)" : "";
                // Convert frequency score (lower is better) to percentage
                double freqPercentage = (1 - freqScores.get(lang)) * 100;
                // Levenshtein score is already in percentage form (higher is better)
                double levPercentage = levScores.get(lang) * 100;
                
                System.out.printf("| %-13s | %9.2f%% | %9.2f%% | %9.2f%% |%s%n",
                    lang.getDisplayName(),
                    freqPercentage,
                    levPercentage,
                    entry.getValue(),
                    marker);
            });
        
        System.out.println("+---------------+-------------+-------------+-------------+");

        // Display letter frequencies
        Map<Character, Double> inputFrequencies = freqService.calculateFrequencies(text);
        Map<Language, Map<Character, Double>> standardFrequencies = freqService.getStandardFrequencies();

        System.out.println("\nLetter Frequencies Comparison:");
        System.out.println("+--------+----------+----------+----------+----------+----------+----------+");
        System.out.println("| Letter | Input    | English  | French   | German   | Italian  | Dutch    |");
        System.out.println("+--------+----------+----------+----------+----------+----------+----------+");

        for (char c = 'a'; c <= 'z'; c++) {
            System.out.printf("| %-6c |", c);
            
            // Input text frequency
            double inputFreq = inputFrequencies.getOrDefault(c, 0.0) * 100;
            System.out.printf(" %8.2f%% |", inputFreq);
            
            // Standard frequencies for each language with color coding
            for (Language lang : Language.values()) {
                Map<Character, Double> langFreq = standardFrequencies.get(lang);
                double standardFreq = langFreq.getOrDefault(c, 0.0) * 100;
                
                // Calculate difference and determine color
                double difference = Math.abs(inputFreq - standardFreq);
                String color = getColorForDifference(difference);
                
                System.out.printf(" %s%8.2f%%%s |", color, standardFreq, RESET);
            }
            System.out.println();
        }
        System.out.println("+--------+----------+----------+----------+----------+----------+----------+");
        
        // Add legend
        System.out.println("\nColor Legend:");
        System.out.printf("%sGreen: Very close match (difference < 0.5%%)%s\n", GREEN, RESET);
        System.out.printf("%sYellow: Moderate match (difference < 1.0%%)%s\n", YELLOW, RESET);
        System.out.printf("%sRed: Large difference (difference > 2.0%%)%s\n", RED, RESET);
    }
    
    private String getColorForDifference(double difference) {
        if (difference < 0.5) {
            return GREEN;  // Very close match
        } else if (difference < 1.0) {
            return YELLOW;  // Moderate match
        } else if (difference > 2.0) {
            return RED;    // Large difference
        }
        return RESET;
    }
}
