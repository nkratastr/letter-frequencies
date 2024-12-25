package com.letterfrequency.ui;

import com.letterfrequency.model.Language;
import com.letterfrequency.service.CombinedLanguageDetectionService;
import com.letterfrequency.service.LanguageDetectionService;
import com.letterfrequency.util.TextValidator;
import java.util.*;

public class ConsoleUI {
    private final Scanner scanner;
    private final LanguageDetectionService languageService;
    
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
        
        displayResults(scores, detectedLanguage);
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
    
    private void displayResults(Map<Language, Double> scores, Language bestMatch) {
        System.out.println("\nLanguage Detection Results:");
        System.out.println("+---------------+-------------+");
        System.out.println("| Language      | Confidence |");
        System.out.println("+---------------+-------------+");
        
        scores.entrySet().stream()
            .sorted(Map.Entry.<Language, Double>comparingByValue().reversed())
            .forEach(entry -> {
                String marker = entry.getKey() == bestMatch ? " (Best Match)" : "";
                System.out.printf("| %-13s | %9.2f%% |%s%n",
                    entry.getKey().getDisplayName(),
                    entry.getValue() * 100,
                    marker);
            });
        
        System.out.println("+---------------+-------------+");
    }
}
