package com.letterfrequency.model;

import java.util.List;
import java.util.Map;
import com.letterfrequency.model.Language;

public class LevenshteinAnalyzer {
    public static int calculateDistance(String word1, String word2) {
        word1 = word1.toLowerCase();
        word2 = word2.toLowerCase();
        
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
                        dp[i - 1][j - 1], // replace
                        Math.min(
                            dp[i - 1][j],   // delete
                            dp[i][j - 1]    // insert
                        )
                    );
                }
            }
        }
        
        return dp[word1.length()][word2.length()];
    }
    
    public static double calculateSimilarity(String word1, String word2) {
        int distance = calculateDistance(word1, word2);
        int maxLength = Math.max(word1.length(), word2.length());
        return maxLength == 0 ? 1.0 : 1.0 - ((double) distance / maxLength);
    }
    
    public static Language findClosestLanguage(String word, Map<Language, List<String>> commonWords) {
        double bestScore = -1;
        Language bestMatch = Language.ENGLISH; // default
        
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
}
