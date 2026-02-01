package com.letterfrequency.service;

import com.letterfrequency.model.Language;
import java.util.*;

public class CombinedLanguageDetectionService implements LanguageDetectionService {
    private final FrequencyAnalysisService frequencyService;
    private final LevenshteinAnalysisService levenshteinService;
    private final IndexOfCoincidenceService icService;
    private final NgramAnalysisService ngramService;
    private final StopwordAnalysisService stopwordService;
    
    // Base weights - adjusted dynamically based on text characteristics
    private static final double BASE_FREQUENCY_WEIGHT = 0.20;
    private static final double BASE_NGRAM_WEIGHT = 0.25;
    private static final double BASE_STOPWORD_WEIGHT = 0.30;
    private static final double BASE_IC_WEIGHT = 0.15;
    private static final double BASE_LEVENSHTEIN_WEIGHT = 0.10;

    public CombinedLanguageDetectionService() {
        this.frequencyService = new FrequencyAnalysisService();
        this.levenshteinService = new LevenshteinAnalysisService();
        this.icService = new IndexOfCoincidenceService();
        this.ngramService = new NgramAnalysisService();
        this.stopwordService = new StopwordAnalysisService();
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
        // Get scores from all services
        Map<Language, Double> frequencyScores = frequencyService.getLanguageScores(text);
        Map<Language, Double> levenshteinScores = levenshteinService.getLanguageScores(text);
        Map<Language, Double> icScores = icService.getLanguageScores(text);
        Map<Language, Double> ngramScores = ngramService.getLanguageScores(text);
        Map<Language, Double> stopwordScores = stopwordService.getLanguageScores(text);
        
        // Convert frequency scores (lower is better) to normalized scores (higher is better)
        Map<Language, Double> normalizedFreqScores = normalizeFrequencyScores(frequencyScores);
        
        // Calculate dynamic weights based on text length
        double[] weights = calculateDynamicWeights(text);
        
        Map<Language, Double> combinedScores = new EnumMap<>(Language.class);
        
        for (Language lang : Language.values()) {
            double freqScore = normalizedFreqScores.getOrDefault(lang, 0.0);
            double levScore = levenshteinScores.getOrDefault(lang, 0.0);
            double icScore = icScores.getOrDefault(lang, 0.0);
            double ngramScore = ngramScores.getOrDefault(lang, 0.0);
            double stopwordScore = stopwordScores.getOrDefault(lang, 0.0);
            
            double combined = (freqScore * weights[0]) + 
                              (ngramScore * weights[1]) + 
                              (stopwordScore * weights[2]) + 
                              (icScore * weights[3]) + 
                              (levScore * weights[4]);
            
            combinedScores.put(lang, combined);
        }
        
        return combinedScores;
    }
    
    /**
     * Convert frequency scores (lower = better) to normalized scores (higher = better).
     */
    private Map<Language, Double> normalizeFrequencyScores(Map<Language, Double> freqScores) {
        Map<Language, Double> normalized = new EnumMap<>(Language.class);
        
        // Find max score for normalization
        double maxScore = freqScores.values().stream()
            .mapToDouble(Double::doubleValue)
            .max()
            .orElse(1.0);
        
        if (maxScore == 0) maxScore = 1.0;
        
        for (Language lang : Language.values()) {
            double score = freqScores.getOrDefault(lang, maxScore);
            // Invert and normalize: lower distance = higher score
            normalized.put(lang, 1.0 - (score / maxScore));
        }
        
        return normalized;
    }
    
    /**
     * Calculate dynamic weights based on text characteristics.
     * Short texts benefit more from word matching, long texts from statistical methods.
     * 
     * @return weights array: [frequency, ngram, stopword, ic, levenshtein]
     */
    private double[] calculateDynamicWeights(String text) {
        int wordCount = text.split("\\s+").length;
        int charCount = text.replaceAll("[^a-zA-Z]", "").length();
        
        // For very short texts (< 50 characters), rely more on word matching
        if (charCount < 50) {
            return new double[] {0.10, 0.15, 0.45, 0.10, 0.20};
        }
        // For short texts (< 200 characters), balanced approach
        else if (charCount < 200) {
            return new double[] {0.15, 0.20, 0.35, 0.15, 0.15};
        }
        // For medium texts (< 500 characters)
        else if (charCount < 500) {
            return new double[] {0.20, 0.25, 0.30, 0.15, 0.10};
        }
        // For long texts, statistical methods are more reliable
        else {
            return new double[] {0.25, 0.30, 0.25, 0.15, 0.05};
        }
    }
    
    // Expose individual services for detailed analysis in UI
    public FrequencyAnalysisService getFrequencyService() {
        return frequencyService;
    }
    
    public NgramAnalysisService getNgramService() {
        return ngramService;
    }
    
    public StopwordAnalysisService getStopwordService() {
        return stopwordService;
    }
    
    public IndexOfCoincidenceService getIcService() {
        return icService;
    }
    
    public LevenshteinAnalysisService getLevenshteinService() {
        return levenshteinService;
    }
}
