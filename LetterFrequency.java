import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LetterFrequency {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder text = new StringBuilder();
        
        System.out.println("Enter or paste your text (press Enter twice to finish):");
        String line;
        String previousLine = null;
        
        while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            
            // Check for two consecutive empty lines to end input
            if (line.trim().isEmpty() && previousLine != null && previousLine.trim().isEmpty()) {
                break;
            }
            
            text.append(line).append("\n");
            previousLine = line;
        }
        
        // Convert text to lowercase to count letters regardless of case
        String processedText = text.toString().toLowerCase();
        
        // Create a map to store letter frequencies
        Map<Character, Integer> frequencies = new HashMap<>();
        
        // Count frequencies
        for (char c : processedText.toCharArray()) {
            if (Character.isLetter(c)) {
                frequencies.put(c, frequencies.getOrDefault(c, 0) + 1);
            }
        }
        
        // Display results
        System.out.println("\nLetter frequencies:");
        frequencies.entrySet().stream()
            .sorted(Map.Entry.comparingByKey())
            .forEach(entry -> System.out.println(entry.getKey() + ": " + entry.getValue()));
            
        scanner.close();
    }
}
