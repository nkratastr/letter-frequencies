package com.letterfrequency.util;

public class TextValidator {
    private final String text;
    
    public TextValidator(String text) {
        this.text = text;
    }
    
    public ValidationResult validate() {
        if (text == null || text.trim().isEmpty()) {
            return new ValidationResult(false, "Text cannot be empty");
        }
        
        if (!containsLetters()) {
            return new ValidationResult(false, "Text must contain at least one letter");
        }
        
        return new ValidationResult(true, "Text is valid");
    }
    
    private boolean containsLetters() {
        return text.chars().anyMatch(Character::isLetter);
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
