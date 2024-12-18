import java.util.HashMap;
import java.util.Map;

public class LanguageFrequencyAnalyzer {
    private static final Map<Character, Double> ENGLISH_FREQUENCIES = new HashMap<>();
    private static final Map<Character, Double> DUTCH_FREQUENCIES = new HashMap<>();
    
    static {
        // English letter frequencies (in percentages)
        ENGLISH_FREQUENCIES.put('e', 12.702);
        ENGLISH_FREQUENCIES.put('t', 9.056);
        ENGLISH_FREQUENCIES.put('a', 8.167);
        ENGLISH_FREQUENCIES.put('o', 7.507);
        ENGLISH_FREQUENCIES.put('i', 6.966);
        ENGLISH_FREQUENCIES.put('n', 6.749);
        ENGLISH_FREQUENCIES.put('s', 6.327);
        ENGLISH_FREQUENCIES.put('h', 6.094);
        ENGLISH_FREQUENCIES.put('r', 5.987);
        ENGLISH_FREQUENCIES.put('d', 4.253);
        ENGLISH_FREQUENCIES.put('l', 4.025);
        ENGLISH_FREQUENCIES.put('c', 2.782);
        ENGLISH_FREQUENCIES.put('u', 2.758);
        ENGLISH_FREQUENCIES.put('m', 2.406);
        ENGLISH_FREQUENCIES.put('w', 2.360);
        ENGLISH_FREQUENCIES.put('f', 2.228);
        ENGLISH_FREQUENCIES.put('g', 2.015);
        ENGLISH_FREQUENCIES.put('y', 1.974);
        ENGLISH_FREQUENCIES.put('p', 1.929);
        ENGLISH_FREQUENCIES.put('b', 1.492);
        ENGLISH_FREQUENCIES.put('v', 0.978);
        ENGLISH_FREQUENCIES.put('k', 0.772);
        ENGLISH_FREQUENCIES.put('j', 0.153);
        ENGLISH_FREQUENCIES.put('x', 0.150);
        ENGLISH_FREQUENCIES.put('q', 0.095);
        ENGLISH_FREQUENCIES.put('z', 0.074);

        // Dutch letter frequencies (in percentages)
        DUTCH_FREQUENCIES.put('e', 18.91);
        DUTCH_FREQUENCIES.put('n', 10.03);
        DUTCH_FREQUENCIES.put('a', 7.49);
        DUTCH_FREQUENCIES.put('t', 6.79);
        DUTCH_FREQUENCIES.put('i', 6.50);
        DUTCH_FREQUENCIES.put('r', 6.41);
        DUTCH_FREQUENCIES.put('o', 6.06);
        DUTCH_FREQUENCIES.put('d', 5.93);
        DUTCH_FREQUENCIES.put('s', 3.73);
        DUTCH_FREQUENCIES.put('l', 3.57);
        DUTCH_FREQUENCIES.put('g', 3.40);
        DUTCH_FREQUENCIES.put('v', 2.85);
        DUTCH_FREQUENCIES.put('h', 2.38);
        DUTCH_FREQUENCIES.put('k', 2.25);
        DUTCH_FREQUENCIES.put('m', 2.21);
        DUTCH_FREQUENCIES.put('u', 1.99);
        DUTCH_FREQUENCIES.put('b', 1.58);
        DUTCH_FREQUENCIES.put('p', 1.57);
        DUTCH_FREQUENCIES.put('w', 1.52);
        DUTCH_FREQUENCIES.put('j', 1.46);
        DUTCH_FREQUENCIES.put('z', 1.39);
        DUTCH_FREQUENCIES.put('c', 1.24);
        DUTCH_FREQUENCIES.put('f', 0.81);
        DUTCH_FREQUENCIES.put('y', 0.035);
        DUTCH_FREQUENCIES.put('x', 0.036);
        DUTCH_FREQUENCIES.put('q', 0.009);
    }

    public static Map<Character, Double> calculatePercentages(Map<Character, Integer> letterCounts, int totalLetters) {
        Map<Character, Double> letterPercentages = new HashMap<>();
        for (Map.Entry<Character, Integer> letterEntry : letterCounts.entrySet()) {
            double percentage = (letterEntry.getValue() * 100.0) / totalLetters;
            letterPercentages.put(letterEntry.getKey(), Math.round(percentage * 1000.0) / 1000.0); // Round to 3 decimal places
        }
        return letterPercentages;
    }

    public static Map<Character, Double> compareWithLanguage(Map<Character, Double> textFrequencies, boolean isDutch) {
        Map<Character, Double> frequencyDifferences = new HashMap<>();
        Map<Character, Double> standardFrequencies = isDutch ? DUTCH_FREQUENCIES : ENGLISH_FREQUENCIES;
        
        for (Map.Entry<Character, Double> letterEntry : textFrequencies.entrySet()) {
            char letter = letterEntry.getKey();
            double textFrequency = letterEntry.getValue();
            double standardFrequency = standardFrequencies.getOrDefault(letter, 0.0);
            double difference = Math.round((textFrequency - standardFrequency) * 1000.0) / 1000.0;
            frequencyDifferences.put(letter, difference);
        }
        
        return frequencyDifferences;
    }

    public static double getStandardFrequency(char letter, boolean isDutch) {
        Map<Character, Double> standardFrequencies = isDutch ? DUTCH_FREQUENCIES : ENGLISH_FREQUENCIES;
        return standardFrequencies.getOrDefault(letter, 0.0);
    }
}
