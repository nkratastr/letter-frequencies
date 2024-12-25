package com.letterfrequency.service;

import com.letterfrequency.model.Language;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.stream.Stream;

public class FrequencyAnalysisService implements LanguageDetectionService {
    private final Map<Language, Map<Character, Double>> standardFrequencies;

    public FrequencyAnalysisService() {
        this.standardFrequencies = initializeStandardFrequencies();
    }

    @Override
    public Language detectLanguage(String text) {
        Map<Language, Double> scores = getLanguageScores(text);
        return scores.entrySet().stream()
            .min(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(Language.ENGLISH);
    }

    @Override
    public Map<Language, Double> getLanguageScores(String text) {
        Map<Character, Double> inputFrequencies = calculateFrequencies(text);
        return compareWithAllLanguages(inputFrequencies);
    }

    private Map<Character, Double> calculateFrequencies(String text) {
        Map<Character, Double> frequencies = new HashMap<>();
        text = text.toLowerCase();
        
        // Count total letters (excluding non-alphabetic characters)
        long totalLetters = text.chars()
            .filter(Character::isLetter)
            .count();
            
        if (totalLetters == 0) {
            return frequencies;
        }

        // Calculate frequencies
        text.chars()
            .filter(Character::isLetter)
            .mapToObj(ch -> (char) ch)
            .forEach(ch -> 
                frequencies.merge(ch, 1.0 / totalLetters, Double::sum));
        
        return frequencies;
    }

    private Map<Language, Double> compareWithAllLanguages(Map<Character, Double> inputFrequencies) {
        Map<Language, Double> scores = new EnumMap<>(Language.class);
        
        for (Map.Entry<Language, Map<Character, Double>> entry : standardFrequencies.entrySet()) {
            Language language = entry.getKey();
            Map<Character, Double> standardFreq = entry.getValue();
            
            // Calculate Euclidean distance
            double score = 0.0;
            for (char c = 'a'; c <= 'z'; c++) {
                double inputFreq = inputFrequencies.getOrDefault(c, 0.0);
                double stdFreq = standardFreq.getOrDefault(c, 0.0);
                score += Math.pow(inputFreq - stdFreq, 2);
            }
            scores.put(language, Math.sqrt(score));
        }
        
        return scores;
    }

    private Map<Language, Map<Character, Double>> initializeStandardFrequencies() {
        Map<Language, Map<Character, Double>> frequencies = new EnumMap<>(Language.class);
        
        // English letter frequencies
        Map<Character, Double> english = new HashMap<>();
        english.put('e', 0.12702); english.put('t', 0.09056); english.put('a', 0.08167);
        english.put('o', 0.07507); english.put('i', 0.06966); english.put('n', 0.06749);
        english.put('s', 0.06327); english.put('h', 0.06094); english.put('r', 0.05987);
        english.put('d', 0.04253); english.put('l', 0.04025); english.put('c', 0.02782);
        frequencies.put(Language.ENGLISH, english);
        
        // French letter frequencies
        Map<Character, Double> french = new HashMap<>();
        french.put('e', 0.14715); french.put('a', 0.07636); french.put('i', 0.07529);
        french.put('s', 0.07948); french.put('n', 0.07095); french.put('r', 0.06553);
        french.put('t', 0.07244); french.put('o', 0.05796); french.put('l', 0.05456);
        french.put('u', 0.06311); french.put('d', 0.03669); french.put('c', 0.03260);
        frequencies.put(Language.FRENCH, french);
        
        // German letter frequencies
        Map<Character, Double> german = new HashMap<>();
        german.put('e', 0.16396); german.put('n', 0.09776); german.put('i', 0.07550);
        german.put('s', 0.07270); german.put('r', 0.07003); german.put('a', 0.06516);
        german.put('t', 0.06154); german.put('d', 0.05076); german.put('h', 0.04768);
        german.put('u', 0.04166); german.put('l', 0.03437); german.put('c', 0.02732);
        frequencies.put(Language.GERMAN, german);
        
        // Italian letter frequencies
        Map<Character, Double> italian = new HashMap<>();
        italian.put('e', 0.11792); italian.put('a', 0.11745); italian.put('i', 0.11263);
        italian.put('o', 0.09832); italian.put('n', 0.06883); italian.put('l', 0.06510);
        italian.put('r', 0.06367); italian.put('t', 0.05623); italian.put('s', 0.04981);
        italian.put('c', 0.04501); italian.put('d', 0.03736); italian.put('p', 0.03056);
        frequencies.put(Language.ITALIAN, italian);
        
        // Dutch letter frequencies
        Map<Character, Double> dutch = new HashMap<>();
        dutch.put('e', 0.18910); dutch.put('n', 0.10032); dutch.put('a', 0.07486);
        dutch.put('t', 0.06790); dutch.put('i', 0.06250); dutch.put('r', 0.06140);
        dutch.put('o', 0.06063); dutch.put('d', 0.05933); dutch.put('s', 0.03730);
        dutch.put('l', 0.03570); dutch.put('g', 0.03403); dutch.put('v', 0.02850);
        frequencies.put(Language.DUTCH, dutch);
        
        return frequencies;
    }
}
