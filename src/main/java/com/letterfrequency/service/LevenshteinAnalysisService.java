package com.letterfrequency.service;

import com.letterfrequency.model.Language;
import java.util.*;

public class LevenshteinAnalysisService implements LanguageDetectionService {
    private final Map<Language, List<String>> commonWords;

    public LevenshteinAnalysisService() {
        this.commonWords = initializeCommonWords();
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
        String[] words = text.toLowerCase().split("\\s+");
        Map<Language, Integer> matches = new EnumMap<>(Language.class);

        for (String word : words) {
            if (word.length() >= 3) {
                Language bestMatch = findClosestLanguage(word);
                matches.merge(bestMatch, 1, Integer::sum);
            }
        }

        return normalizeScores(matches);
    }

    private Language findClosestLanguage(String word) {
        double bestScore = -1;
        Language bestMatch = Language.ENGLISH;

        for (Map.Entry<Language, List<String>> entry : commonWords.entrySet()) {
            double languageScore = entry.getValue().stream()
                .mapToDouble(commonWord -> calculateSimilarity(word, commonWord))
                .max()
                .orElse(0.0);

            if (languageScore > bestScore) {
                bestScore = languageScore;
                bestMatch = entry.getKey();
            }
        }

        return bestMatch;
    }

    private double calculateSimilarity(String word1, String word2) {
        int distance = calculateDistance(word1, word2);
        int maxLength = Math.max(word1.length(), word2.length());
        return maxLength == 0 ? 1.0 : 1.0 - ((double) distance / maxLength);
    }

    private int calculateDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        for (int i = 0; i <= word1.length(); i++) {
            dp[i][0] = i;
        }
        for (int j = 0; j <= word2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                if (word1.charAt(i - 1) == word2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                        dp[i - 1][j - 1],
                        Math.min(dp[i - 1][j], dp[i][j - 1])
                    );
                }
            }
        }

        return dp[word1.length()][word2.length()];
    }

    private Map<Language, Double> normalizeScores(Map<Language, Integer> matches) {
        Map<Language, Double> normalizedScores = new EnumMap<>(Language.class);
        int total = matches.values().stream().mapToInt(Integer::intValue).sum();

        if (total > 0) {
            matches.forEach((lang, count) ->
                normalizedScores.put(lang, (double) count / total));
        }

        return normalizedScores;
    }

    private Map<Language, List<String>> initializeCommonWords() {
        Map<Language, List<String>> words = new EnumMap<>(Language.class);
        
        words.put(Language.ENGLISH, Arrays.asList(
            "the", "be", "to", "of", "and", "a", "in", "that", "have", "i"
        ));
        words.put(Language.FRENCH, Arrays.asList(
            "le", "la", "les", "de", "et", "être", "avoir", "que", "pour", "dans"
        ));
        words.put(Language.GERMAN, Arrays.asList(
            "der", "die", "das", "und", "in", "sein", "zu", "haben", "mit", "für"
        ));
        words.put(Language.ITALIAN, Arrays.asList(
            "il", "di", "che", "è", "la", "in", "non", "un", "per", "sono"
        ));
        words.put(Language.DUTCH, Arrays.asList(
            "de", "het", "een", "van", "en", "in", "is", "dat", "op", "te"
        ));

        return words;
    }
}
