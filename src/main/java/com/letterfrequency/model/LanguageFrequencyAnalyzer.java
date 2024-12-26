package com.letterfrequency.model;
import java.util.HashMap;
import java.util.Map;
import java.util.EnumMap;

public class LanguageFrequencyAnalyzer {
    private static final Map<String, Map<Character, Double>> LANGUAGE_FREQUENCIES = new HashMap<>();

    static {
        // English letter frequencies
        Map<Character, Double> englishFreq = new HashMap<>();
        englishFreq.put('e', 12.702);
        englishFreq.put('t', 9.056);
        englishFreq.put('a', 8.167);
        englishFreq.put('o', 7.507);
        englishFreq.put('i', 6.966);
        englishFreq.put('n', 6.749);
        englishFreq.put('s', 6.327);
        englishFreq.put('h', 6.094);
        englishFreq.put('r', 5.987);
        englishFreq.put('d', 4.253);
        englishFreq.put('l', 4.025);
        englishFreq.put('c', 2.782);
        englishFreq.put('u', 2.758);
        englishFreq.put('m', 2.406);
        englishFreq.put('w', 2.360);
        englishFreq.put('f', 2.228);
        englishFreq.put('g', 2.015);
        englishFreq.put('y', 1.974);
        englishFreq.put('p', 1.929);
        englishFreq.put('b', 1.492);
        englishFreq.put('v', 0.978);
        englishFreq.put('k', 0.772);
        englishFreq.put('j', 0.153);
        englishFreq.put('x', 0.150);
        englishFreq.put('q', 0.095);
        englishFreq.put('z', 0.074);
        LANGUAGE_FREQUENCIES.put("ENGLISH", englishFreq);

        // Dutch letter frequencies
        Map<Character, Double> dutchFreq = new HashMap<>();
        dutchFreq.put('e', 18.91);
        dutchFreq.put('n', 10.03);
        dutchFreq.put('a', 7.49);
        dutchFreq.put('t', 6.79);
        dutchFreq.put('i', 6.50);
        dutchFreq.put('r', 6.41);
        dutchFreq.put('o', 6.06);
        dutchFreq.put('d', 5.93);
        dutchFreq.put('s', 3.73);
        dutchFreq.put('l', 3.57);
        dutchFreq.put('g', 3.40);
        dutchFreq.put('v', 2.85);
        dutchFreq.put('h', 2.38);
        dutchFreq.put('k', 2.25);
        dutchFreq.put('m', 2.21);
        dutchFreq.put('u', 1.99);
        dutchFreq.put('b', 1.58);
        dutchFreq.put('p', 1.57);
        dutchFreq.put('w', 1.52);
        dutchFreq.put('j', 1.46);
        dutchFreq.put('z', 1.39);
        dutchFreq.put('c', 1.24);
        dutchFreq.put('f', 0.81);
        dutchFreq.put('y', 0.035);
        dutchFreq.put('x', 0.04);
        dutchFreq.put('q', 0.009);
        LANGUAGE_FREQUENCIES.put("DUTCH", dutchFreq);

        // French letter frequencies
        Map<Character, Double> frenchFreq = new HashMap<>();
        frenchFreq.put('e', 14.715);
        frenchFreq.put('a', 7.636);
        frenchFreq.put('i', 7.529);
        frenchFreq.put('s', 7.948);
        frenchFreq.put('n', 7.095);
        frenchFreq.put('r', 6.693);
        frenchFreq.put('t', 7.244);
        frenchFreq.put('o', 5.796);
        frenchFreq.put('l', 5.456);
        frenchFreq.put('u', 6.311);
        frenchFreq.put('d', 3.669);
        frenchFreq.put('c', 3.260);
        frenchFreq.put('m', 2.968);
        frenchFreq.put('p', 2.521);
        frenchFreq.put('g', 1.043);
        frenchFreq.put('b', 0.901);
        frenchFreq.put('v', 1.838);
        frenchFreq.put('h', 0.737);
        frenchFreq.put('f', 1.066);
        frenchFreq.put('q', 1.362);
        frenchFreq.put('y', 0.128);
        frenchFreq.put('x', 0.427);
        frenchFreq.put('j', 0.545);
        frenchFreq.put('k', 0.049);
        frenchFreq.put('w', 0.114);
        frenchFreq.put('z', 0.326);
        LANGUAGE_FREQUENCIES.put("FRENCH", frenchFreq);

        // German letter frequencies
        Map<Character, Double> germanFreq = new HashMap<>();
        germanFreq.put('e', 16.396);
        germanFreq.put('n', 9.776);
        germanFreq.put('i', 7.550);
        germanFreq.put('s', 7.270);
        germanFreq.put('r', 7.003);
        germanFreq.put('a', 6.516);
        germanFreq.put('t', 6.154);
        germanFreq.put('d', 5.076);
        germanFreq.put('h', 4.577);
        germanFreq.put('u', 4.166);
        germanFreq.put('l', 3.437);
        germanFreq.put('c', 2.732);
        germanFreq.put('g', 3.009);
        germanFreq.put('m', 2.534);
        germanFreq.put('o', 2.510);
        germanFreq.put('b', 1.886);
        germanFreq.put('w', 1.921);
        germanFreq.put('f', 1.656);
        germanFreq.put('k', 1.417);
        germanFreq.put('z', 1.134);
        germanFreq.put('p', 0.670);
        germanFreq.put('v', 0.846);
        germanFreq.put('j', 0.268);
        germanFreq.put('y', 0.039);
        germanFreq.put('x', 0.034);
        germanFreq.put('q', 0.018);
        LANGUAGE_FREQUENCIES.put("GERMAN", germanFreq);

        // Italian letter frequencies
        Map<Character, Double> italianFreq = new HashMap<>();
        italianFreq.put('e', 11.792);
        italianFreq.put('a', 11.745);
        italianFreq.put('i', 11.285);
        italianFreq.put('o', 9.832);
        italianFreq.put('n', 6.883);
        italianFreq.put('l', 6.510);
        italianFreq.put('r', 6.367);
        italianFreq.put('t', 5.623);
        italianFreq.put('s', 4.981);
        italianFreq.put('c', 4.501);
        italianFreq.put('d', 3.736);
        italianFreq.put('p', 3.056);
        italianFreq.put('u', 3.011);
        italianFreq.put('m', 2.512);
        italianFreq.put('v', 2.097);
        italianFreq.put('g', 1.644);
        italianFreq.put('h', 1.539);
        italianFreq.put('b', 0.927);
        italianFreq.put('f', 1.153);
        italianFreq.put('z', 0.495);
        italianFreq.put('q', 0.505);
        italianFreq.put('w', 0.033);
        italianFreq.put('y', 0.020);
        italianFreq.put('k', 0.009);
        italianFreq.put('x', 0.003);
        italianFreq.put('j', 0.001);
        LANGUAGE_FREQUENCIES.put("ITALIAN", italianFreq);
    }

    public static Map<Character, Double> calculatePercentages(Map<Character, Integer> letterCounts, int totalLetters) {
        Map<Character, Double> letterPercentages = new HashMap<>();
        for (Map.Entry<Character, Integer> letterEntry : letterCounts.entrySet()) {
            double percentage = (letterEntry.getValue() * 100.0) / totalLetters;
            letterPercentages.put(letterEntry.getKey(), Math.round(percentage * 1000.0) / 1000.0);
        }
        return letterPercentages;
    }

    public static double getStandardFrequency(char letter, String language) {
        Map<Character, Double> standardFrequencies = LANGUAGE_FREQUENCIES.get(language);
        return standardFrequencies != null ? standardFrequencies.getOrDefault(letter, 0.0) : 0.0;
    }

    public static Map<Character, Double> compareWithLanguage(Map<Character, Double> textFrequencies, String language) {
        Map<Character, Double> frequencyDifferences = new HashMap<>();
        Map<Character, Double> standardFrequencies = LANGUAGE_FREQUENCIES.get(language);
        
        if (standardFrequencies != null) {
            for (Map.Entry<Character, Double> letterEntry : textFrequencies.entrySet()) {
                char letter = letterEntry.getKey();
                double textFrequency = letterEntry.getValue();
                double standardFrequency = standardFrequencies.getOrDefault(letter, 0.0);
                double difference = Math.round((textFrequency - standardFrequency) * 1000.0) / 1000.0;
                frequencyDifferences.put(letter, difference);
            }
        }
        
        return frequencyDifferences;
    }
}
