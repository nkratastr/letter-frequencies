package com.letterfrequency.service;

import com.letterfrequency.model.Language;
import java.util.*;

/**
 * Language detection service based on N-gram analysis.
 * Analyzes character bigrams (2-letter sequences) which are highly characteristic of each language.
 * For example: "th" is common in English, "ij" in Dutch, "ch" in German, etc.
 */
public class NgramAnalysisService implements LanguageDetectionService {
    
    private final Map<Language, Map<String, Double>> languageBigrams;
    
    public NgramAnalysisService() {
        this.languageBigrams = initializeLanguageBigrams();
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
        Map<String, Double> inputBigrams = calculateBigrams(text);
        Map<Language, Double> scores = new EnumMap<>(Language.class);
        
        for (Language lang : Language.values()) {
            double similarity = calculateCosineSimilarity(inputBigrams, languageBigrams.get(lang));
            scores.put(lang, similarity);
        }
        
        return scores;
    }
    
    /**
     * Calculate bigram frequencies from input text.
     */
    public Map<String, Double> calculateBigrams(String text) {
        Map<String, Integer> bigramCounts = new HashMap<>();
        String cleaned = text.toLowerCase().replaceAll("[^a-z]", "");
        int total = 0;
        
        for (int i = 0; i < cleaned.length() - 1; i++) {
            String bigram = cleaned.substring(i, i + 2);
            bigramCounts.merge(bigram, 1, Integer::sum);
            total++;
        }
        
        Map<String, Double> frequencies = new HashMap<>();
        if (total > 0) {
            for (Map.Entry<String, Integer> entry : bigramCounts.entrySet()) {
                frequencies.put(entry.getKey(), (double) entry.getValue() / total);
            }
        }
        
        return frequencies;
    }
    
    /**
     * Calculate cosine similarity between two bigram frequency maps.
     */
    private double calculateCosineSimilarity(Map<String, Double> map1, Map<String, Double> map2) {
        Set<String> allBigrams = new HashSet<>();
        allBigrams.addAll(map1.keySet());
        allBigrams.addAll(map2.keySet());
        
        double dotProduct = 0.0;
        double norm1 = 0.0;
        double norm2 = 0.0;
        
        for (String bigram : allBigrams) {
            double val1 = map1.getOrDefault(bigram, 0.0);
            double val2 = map2.getOrDefault(bigram, 0.0);
            
            dotProduct += val1 * val2;
            norm1 += val1 * val1;
            norm2 += val2 * val2;
        }
        
        if (norm1 == 0 || norm2 == 0) {
            return 0.0;
        }
        
        return dotProduct / (Math.sqrt(norm1) * Math.sqrt(norm2));
    }
    
    /**
     * Initialize characteristic bigram frequencies for each language.
     * These are the most common and distinctive bigrams for each language.
     */
    private Map<Language, Map<String, Double>> initializeLanguageBigrams() {
        Map<Language, Map<String, Double>> bigrams = new EnumMap<>(Language.class);
        
        // English bigrams (most frequent)
        Map<String, Double> english = new HashMap<>();
        english.put("th", 0.0356); english.put("he", 0.0307); english.put("in", 0.0243);
        english.put("er", 0.0205); english.put("an", 0.0199); english.put("re", 0.0185);
        english.put("on", 0.0176); english.put("at", 0.0149); english.put("en", 0.0145);
        english.put("nd", 0.0135); english.put("ti", 0.0134); english.put("es", 0.0134);
        english.put("or", 0.0128); english.put("te", 0.0120); english.put("of", 0.0117);
        english.put("ed", 0.0117); english.put("is", 0.0113); english.put("it", 0.0112);
        english.put("al", 0.0109); english.put("ar", 0.0107); english.put("st", 0.0105);
        english.put("to", 0.0104); english.put("nt", 0.0104); english.put("ng", 0.0095);
        english.put("se", 0.0093); english.put("ha", 0.0093); english.put("as", 0.0087);
        english.put("ou", 0.0087); english.put("io", 0.0083); english.put("le", 0.0083);
        bigrams.put(Language.ENGLISH, english);
        
        // Dutch bigrams
        Map<String, Double> dutch = new HashMap<>();
        dutch.put("en", 0.0498); dutch.put("de", 0.0339); dutch.put("an", 0.0256);
        dutch.put("er", 0.0251); dutch.put("ee", 0.0214); dutch.put("et", 0.0203);
        dutch.put("te", 0.0199); dutch.put("ge", 0.0195); dutch.put("nd", 0.0183);
        dutch.put("in", 0.0182); dutch.put("he", 0.0175); dutch.put("ij", 0.0171);
        dutch.put("va", 0.0168); dutch.put("aa", 0.0165); dutch.put("ve", 0.0158);
        dutch.put("oo", 0.0155); dutch.put("da", 0.0147); dutch.put("ie", 0.0145);
        dutch.put("or", 0.0140); dutch.put("ng", 0.0138); dutch.put("op", 0.0135);
        dutch.put("is", 0.0132); dutch.put("st", 0.0129); dutch.put("re", 0.0125);
        dutch.put("el", 0.0122); dutch.put("ni", 0.0118); dutch.put("me", 0.0115);
        dutch.put("we", 0.0112); dutch.put("be", 0.0108); dutch.put("al", 0.0105);
        bigrams.put(Language.DUTCH, dutch);
        
        // French bigrams
        Map<String, Double> french = new HashMap<>();
        french.put("es", 0.0315); french.put("en", 0.0265); french.put("de", 0.0248);
        french.put("le", 0.0223); french.put("re", 0.0215); french.put("on", 0.0208);
        french.put("nt", 0.0205); french.put("la", 0.0198); french.put("er", 0.0195);
        french.put("te", 0.0188); french.put("ou", 0.0185); french.put("an", 0.0178);
        french.put("se", 0.0172); french.put("ai", 0.0168); french.put("qu", 0.0165);
        french.put("et", 0.0160); french.put("it", 0.0155); french.put("is", 0.0150);
        french.put("ur", 0.0145); french.put("ti", 0.0140); french.put("ie", 0.0135);
        french.put("me", 0.0130); french.put("ne", 0.0125); french.put("pa", 0.0120);
        french.put("ra", 0.0115); french.put("us", 0.0110); french.put("ue", 0.0105);
        french.put("ns", 0.0100); french.put("ce", 0.0095); french.put("co", 0.0090);
        bigrams.put(Language.FRENCH, french);
        
        // German bigrams
        Map<String, Double> german = new HashMap<>();
        german.put("en", 0.0415); german.put("er", 0.0378); german.put("ch", 0.0275);
        german.put("de", 0.0248); german.put("ei", 0.0231); german.put("nd", 0.0215);
        german.put("te", 0.0205); german.put("in", 0.0198); german.put("ie", 0.0192);
        german.put("ge", 0.0185); german.put("un", 0.0178); german.put("st", 0.0172);
        german.put("es", 0.0165); german.put("an", 0.0158); german.put("he", 0.0152);
        german.put("be", 0.0148); german.put("sc", 0.0142); german.put("ng", 0.0138);
        german.put("au", 0.0132); german.put("ic", 0.0128); german.put("ne", 0.0122);
        german.put("se", 0.0118); german.put("re", 0.0112); german.put("di", 0.0108);
        german.put("it", 0.0102); german.put("ni", 0.0098); german.put("da", 0.0095);
        german.put("ht", 0.0092); german.put("as", 0.0088); german.put("is", 0.0085);
        bigrams.put(Language.GERMAN, german);
        
        // Italian bigrams
        Map<String, Double> italian = new HashMap<>();
        italian.put("er", 0.0298); italian.put("re", 0.0275); italian.put("on", 0.0258);
        italian.put("di", 0.0245); italian.put("en", 0.0232); italian.put("to", 0.0225);
        italian.put("an", 0.0218); italian.put("ta", 0.0212); italian.put("la", 0.0205);
        italian.put("te", 0.0198); italian.put("in", 0.0192); italian.put("ti", 0.0185);
        italian.put("no", 0.0178); italian.put("co", 0.0172); italian.put("ne", 0.0168);
        italian.put("ch", 0.0162); italian.put("al", 0.0158); italian.put("el", 0.0152);
        italian.put("ra", 0.0148); italian.put("le", 0.0142); italian.put("io", 0.0138);
        italian.put("de", 0.0132); italian.put("na", 0.0128); italian.put("li", 0.0122);
        italian.put("ia", 0.0118); italian.put("pe", 0.0112); italian.put("se", 0.0108);
        italian.put("at", 0.0102); italian.put("ri", 0.0098); italian.put("si", 0.0095);
        bigrams.put(Language.ITALIAN, italian);
        
        return bigrams;
    }
}
