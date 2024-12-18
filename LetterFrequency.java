import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LetterFrequency {
    public static void main(String[] args) {
        Scanner textInputScanner = new Scanner(System.in);
        StringBuilder textInputBuilder = new StringBuilder();
        
        System.out.println("Enter or paste your text (press Enter twice to finish):");
        String currentInputLine;
        String previousInputLine = null;
        
        while (textInputScanner.hasNextLine()) {
            currentInputLine = textInputScanner.nextLine();
            
            // Check for two consecutive empty lines to end input
            if (currentInputLine.trim().isEmpty() && previousInputLine != null && previousInputLine.trim().isEmpty()) {
                break;
            }
            
            textInputBuilder.append(currentInputLine).append("\n");
            previousInputLine = currentInputLine;
        }
        
        // Convert text to lowercase to count letters regardless of case
        String normalizedText = textInputBuilder.toString().toLowerCase();
        
        // Validate the input text
        TextValidator validator = new TextValidator(normalizedText);
        TextValidator.ValidationResult validationResult = validator.validate();
        
        if (!validationResult.isValid()) {
            System.out.println(validationResult.getMessage());
            textInputScanner.close();
            return;
        }
        
        // Create a map to store letter frequencies
        Map<Character, Integer> letterFrequencyMap = new HashMap<>();
        
        // Count frequencies, total letters and total characters
        int totalLetters = 0;
        int totalCharacters = 0;
        for (char currentChar : normalizedText.toCharArray()) {
            if (Character.isLetter(currentChar)) {
                letterFrequencyMap.put(currentChar, letterFrequencyMap.getOrDefault(currentChar, 0) + 1);
                totalLetters++;
            }
            if (currentChar != '\n') {  // Don't count newline characters
                totalCharacters++;
            }
        }
        
        // Count words
        String[] words = normalizedText.split("\\s+");
        int wordCount = 0;
        for (String word : words) {
            if (!word.trim().isEmpty()) {
                wordCount++;
            }
        }
        
        // Display summary
        System.out.println("\nText Analysis Summary:");
        System.out.println("Total words: " + wordCount);
        System.out.println("Total letters: " + totalLetters);
        System.out.println("Total characters (including spaces, punctuation): " + totalCharacters);
        
        // Calculate frequencies and percentages
        Map<Character, Double> letterFrequencyPercentages = LanguageFrequencyAnalyzer.calculatePercentages(letterFrequencyMap, totalLetters);
        
        // Display letter frequencies table
        System.out.println("\nLetter Frequency Analysis:");
        System.out.println("+---------+----------+-----------+---------------+---------------+");
        System.out.println("| Letter  | Count    | Your Text | English Std % | Dutch Std %   |");
        System.out.println("+---------+----------+-----------+---------------+---------------+");
        
        letterFrequencyMap.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(letterEntry -> {
                char letter = letterEntry.getKey();
                int count = letterEntry.getValue();
                double percentage = letterFrequencyPercentages.get(letter);
                double englishStandardFreq = LanguageFrequencyAnalyzer.getStandardFrequency(letter, false);
                double dutchStandardFreq = LanguageFrequencyAnalyzer.getStandardFrequency(letter, true);
                
                System.out.printf("| %-7c | %8d | %9.3f%% | %11.3f%% | %11.3f%% |\n",
                    letter, count, percentage, englishStandardFreq, dutchStandardFreq);
            });
            
        System.out.println("+---------+----------+-----------+---------------+---------------+");
        
        // Display differences analysis
        System.out.println("\nDifference Analysis (+ means more frequent in your text, - means less frequent):");
        System.out.println("+---------+----------------+---------------+");
        System.out.println("| Letter  | vs English     | vs Dutch      |");
        System.out.println("+---------+----------------+---------------+");
        
        letterFrequencyMap.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(letterEntry -> {
                char letter = letterEntry.getKey();
                double percentage = letterFrequencyPercentages.get(letter);
                double englishDifference = percentage - LanguageFrequencyAnalyzer.getStandardFrequency(letter, false);
                double dutchDifference = percentage - LanguageFrequencyAnalyzer.getStandardFrequency(letter, true);
                
                System.out.printf("| %-7c | %+13.3f%% | %+12.3f%% |\n",
                    letter, englishDifference, dutchDifference);
            });
            
        System.out.println("+---------+----------------+---------------+");
        
        textInputScanner.close();
    }
}
