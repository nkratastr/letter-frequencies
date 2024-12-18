public class TextValidator {
    private final String text;
    
    public TextValidator(String text) {
        this.text = text;
    }
    
    public boolean isEmpty() {
        return text == null || text.trim().isEmpty();
    }
    
    public boolean containsLetters() {
        if (isEmpty()) {
            return false;
        }
        return text.chars().anyMatch(Character::isLetter);
    }
    
    public boolean containsOnlyNumbers() {
        if (isEmpty()) {
            return false;
        }
        return text.chars()
            .filter(ch -> !Character.isWhitespace(ch))
            .allMatch(Character::isDigit);
    }
    
    public boolean containsOnlySpecialCharacters() {
        if (isEmpty()) {
            return false;
        }
        return text.chars()
            .filter(ch -> !Character.isWhitespace(ch))
            .noneMatch(ch -> Character.isLetterOrDigit(ch));
    }
    
    public ValidationResult validate() {
        if (isEmpty()) {
            return new ValidationResult(false, "Error: The text is empty. Please enter some text.");
        }
        
        if (!containsLetters()) {
            if (containsOnlyNumbers()) {
                return new ValidationResult(false, "Error: The text contains only numbers. Please include some letters for frequency analysis.");
            }
            if (containsOnlySpecialCharacters()) {
                return new ValidationResult(false, "Error: The text contains only special characters. Please include some letters for frequency analysis.");
            }
            return new ValidationResult(false, "Error: No letters found in the text. Please include some letters for frequency analysis.");
        }
        
        return new ValidationResult(true, "Text validation successful.");
    }
    
    public static class ValidationResult {
        private final boolean valid;
        private final String message;
        
        public ValidationResult(boolean valid, String message) {
            this.valid = valid;
            this.message = message;
        }
        
        public boolean isValid() {
            return valid;
        }
        
        public String getMessage() {
            return message;
        }
    }
}
