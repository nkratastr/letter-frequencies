package com.letterfrequency.service;

import com.letterfrequency.model.Language;
import java.util.*;

/**
 * Language detection service based on Index of Coincidence (IC).
 * IC measures the probability that two randomly selected letters from a text are identical.
 * Each language has a characteristic IC value based on its letter frequency distribution.
 */
public class IndexOfCoincidenceService implements LanguageDetectionService {
    
    // Expected IC values for each language (based on linguistic research)
    private final Map<Language, Double> expectedIC;
    
    public IndexOfCoincidenceService() {
        this.expectedIC = initializeExpectedIC();
    }
    
    private Map<Language, Double> initializeExpectedIC() {
        Map<Language, Double> ic = new EnumMap<>(Language.class);
        ic.put(Language.ENGLISH, 0.0667);
        ic.put(Language.FRENCH, 0.0778);
        ic.put(Language.GERMAN, 0.0762);
        ic.put(Language.ITALIAN, 0.0738);
        ic.put(Language.DUTCH, 0.0798);
        return ic;
    }
    
    @Override
    public Language detectLanguage(String text) {
        Map<Language, Double> scores = getLanguageScores(text);
        return scores.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(Language.ENGLISH);
    }
    
    @Override
    public Map<Language, Double> getLanguageScores(String text) {
        double calculatedIC = calculateIC(text);
        Map<Language, Double> scores = new EnumMap<>(Language.class);
        
        // Score each language based on how close the calculated IC is to expected IC
        // Using inverse of absolute difference, normalized
        double maxDifference = 0.05; // Maximum expected difference for normalization
        
        for (Language lang : Language.values()) {
            double expectedValue = expectedIC.get(lang);
            double difference = Math.abs(calculatedIC - expectedValue);
            // Convert difference to a similarity score (0-1, higher is better)
            double similarity = Math.max(0, 1 - (difference / maxDifference));
            scores.put(lang, similarity);
        }
        
        return scores;
    }
    
    /**
     * Calculate the Index of Coincidence for the given text.
     * Formula: IC = Σ(n_i * (n_i - 1)) / (N * (N - 1))
     * where n_i is the count of each letter and N is total letter count.
     * 
     * @param text The input text to analyze
     * @return The calculated IC value
     */
    public double calculateIC(String text) {
        // Count letter frequencies
        Map<Character, Integer> letterCounts = new HashMap<>();
        int totalLetters = 0;
        
        for (char c : text.toLowerCase().toCharArray()) {
            if (c >= 'a' && c <= 'z') {
                letterCounts.merge(c, 1, Integer::sum);
                totalLetters++;
            }
        }
        
        if (totalLetters <= 1) {
            return 0.0; // Not enough letters to calculate IC
        }
        
        // Calculate IC using the formula: Σ(n_i * (n_i - 1)) / (N * (N - 1))
        double numerator = 0;
        for (int count : letterCounts.values()) {
            numerator += count * (count - 1);
        }
        
        double denominator = (double) totalLetters * (totalLetters - 1);
        
        return numerator / denominator;
    }
    
    /**
     * Get the expected IC value for a specific language.
     * 
     * @param language The language to get the expected IC for
     * @return The expected IC value
     */
    public double getExpectedIC(Language language) {
        return expectedIC.getOrDefault(language, 0.0667);
    }
    
    /**
     * Get all expected IC values.
     * 
     * @return Map of languages to their expected IC values
     */
    public Map<Language, Double> getExpectedICValues() {
        return new EnumMap<>(expectedIC);
    }
}
