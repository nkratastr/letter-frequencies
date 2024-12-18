import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class LetterFrequency {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("Enter your text:");
        String text = scanner.nextLine();
        
        // Convert text to lowercase to count letters regardless of case
        text = text.toLowerCase();
        
        // Create a map to store letter frequencies
        Map<Character, Integer> frequencies = new HashMap<>();
        
        // Count frequencies
        for (char c : text.toCharArray()) {
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
