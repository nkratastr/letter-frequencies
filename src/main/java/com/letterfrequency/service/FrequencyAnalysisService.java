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

    public Map<Character, Double> calculateFrequencies(String text) {
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

    public Map<Language, Map<Character, Double>> getStandardFrequencies() {
        return standardFrequencies;
    }

    private Map<Language, Map<Character, Double>> initializeStandardFrequencies() {
        Map<Language, Map<Character, Double>> frequencies = new EnumMap<>(Language.class);
        
        // English letter frequencies
        Map<Character, Double> english = new HashMap<>();
        english.put('a', 0.0817); english.put('b', 0.0150); english.put('c', 0.0278);
        english.put('d', 0.0425); english.put('e', 0.1270); english.put('f', 0.0223);
        english.put('g', 0.0202); english.put('h', 0.0609); english.put('i', 0.0697);
        english.put('j', 0.0015); english.put('k', 0.0077); english.put('l', 0.0403);
        english.put('m', 0.0241); english.put('n', 0.0675); english.put('o', 0.0751);
        english.put('p', 0.0193); english.put('q', 0.0010); english.put('r', 0.0599);
        english.put('s', 0.0633); english.put('t', 0.0906); english.put('u', 0.0276);
        english.put('v', 0.0098); english.put('w', 0.0236); english.put('x', 0.0015);
        english.put('y', 0.0197); english.put('z', 0.0007);
        frequencies.put(Language.ENGLISH, english);
        
        // French letter frequencies
        Map<Character, Double> french = new HashMap<>();
        french.put('a', 0.0764); french.put('b', 0.0090); french.put('c', 0.0326);
        french.put('d', 0.0367); french.put('e', 0.1472); french.put('f', 0.0107);
        french.put('g', 0.0087); french.put('h', 0.0074); french.put('i', 0.0753);
        french.put('j', 0.0054); french.put('k', 0.0002); french.put('l', 0.0546);
        french.put('m', 0.0297); french.put('n', 0.0710); french.put('o', 0.0580);
        french.put('p', 0.0252); french.put('q', 0.0136); french.put('r', 0.0655);
        french.put('s', 0.0795); french.put('t', 0.0724); french.put('u', 0.0631);
        french.put('v', 0.0164); french.put('w', 0.0001); french.put('x', 0.0039);
        french.put('y', 0.0128); french.put('z', 0.0012);
        frequencies.put(Language.FRENCH, french);
        
        // German letter frequencies
        Map<Character, Double> german = new HashMap<>();
        german.put('a', 0.0652); german.put('b', 0.0189); german.put('c', 0.0273);
        german.put('d', 0.0508); german.put('e', 0.1640); german.put('f', 0.0166);
        german.put('g', 0.0301); german.put('h', 0.0477); german.put('i', 0.0755);
        german.put('j', 0.0027); german.put('k', 0.0121); german.put('l', 0.0344);
        german.put('m', 0.0253); german.put('n', 0.0978); german.put('o', 0.0251);
        german.put('p', 0.0079); german.put('q', 0.0002); german.put('r', 0.0700);
        german.put('s', 0.0727); german.put('t', 0.0615); german.put('u', 0.0417);
        german.put('v', 0.0067); german.put('w', 0.0189); german.put('x', 0.0003);
        german.put('y', 0.0004); german.put('z', 0.0113);
        frequencies.put(Language.GERMAN, german);
        
        // Italian letter frequencies
        Map<Character, Double> italian = new HashMap<>();
        italian.put('a', 0.1175); italian.put('b', 0.0092); italian.put('c', 0.0450);
        italian.put('d', 0.0374); italian.put('e', 0.1179); italian.put('f', 0.0095);
        italian.put('g', 0.0164); italian.put('h', 0.0154); italian.put('i', 0.1126);
        italian.put('j', 0.0001); italian.put('k', 0.0001); italian.put('l', 0.0651);
        italian.put('m', 0.0251); italian.put('n', 0.0688); italian.put('o', 0.0983);
        italian.put('p', 0.0306); italian.put('q', 0.0051); italian.put('r', 0.0637);
        italian.put('s', 0.0498); italian.put('t', 0.0562); italian.put('u', 0.0301);
        italian.put('v', 0.0210); italian.put('w', 0.0001); italian.put('x', 0.0001);
        italian.put('y', 0.0001); italian.put('z', 0.0049);
        frequencies.put(Language.ITALIAN, italian);
        
        // Dutch letter frequencies
        Map<Character, Double> dutch = new HashMap<>();
        dutch.put('a', 0.0749); dutch.put('b', 0.0158); dutch.put('c', 0.0124);
        dutch.put('d', 0.0593); dutch.put('e', 0.1891); dutch.put('f', 0.0081);
        dutch.put('g', 0.0340); dutch.put('h', 0.0238); dutch.put('i', 0.0625);
        dutch.put('j', 0.0146); dutch.put('k', 0.0225); dutch.put('l', 0.0357);
        dutch.put('m', 0.0221); dutch.put('n', 0.1003); dutch.put('o', 0.0606);
        dutch.put('p', 0.0157); dutch.put('q', 0.0009); dutch.put('r', 0.0614);
        dutch.put('s', 0.0373); dutch.put('t', 0.0679); dutch.put('u', 0.0199);
        dutch.put('v', 0.0285); dutch.put('w', 0.0152); dutch.put('x', 0.0004);
        dutch.put('y', 0.0035); dutch.put('z', 0.0139);
        frequencies.put(Language.DUTCH, dutch);

        return frequencies;
    }
}
