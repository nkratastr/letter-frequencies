package com.letterfrequency.service;

import com.letterfrequency.model.Language;
import java.util.*;

public class CombinedLanguageDetectionService implements LanguageDetectionService {
    private final FrequencyAnalysisService frequencyService;
    private final LevenshteinAnalysisService levenshteinService;
    private final double FREQUENCY_WEIGHT = 0.6;
    private final double LEVENSHTEIN_WEIGHT = 0.4;

    public CombinedLanguageDetectionService() {
        this.frequencyService = new FrequencyAnalysisService();
        this.levenshteinService = new LevenshteinAnalysisService();
    }

    @Override
    public Language detectLanguage(String text) {
        Map<Language, Double> combinedScores = getLanguageScores(text);
        return combinedScores.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(Language.ENGLISH);
    }

    @Override
    public Map<Language, Double> getLanguageScores(String text) {
        Map<Language, Double> frequencyScores = frequencyService.getLanguageScores(text);
        Map<Language, Double> levenshteinScores = levenshteinService.getLanguageScores(text);
        
        Map<Language, Double> combinedScores = new EnumMap<>(Language.class);
        
        for (Language lang : Language.values()) {
            double freqScore = frequencyScores.getOrDefault(lang, 0.0);
            double levScore = levenshteinScores.getOrDefault(lang, 0.0);
            
            combinedScores.put(lang, 
                (freqScore * FREQUENCY_WEIGHT) + (levScore * LEVENSHTEIN_WEIGHT));
        }
        
        return combinedScores;
    }
}
