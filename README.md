# Letter Frequency Calculator

A Java program that calculates the frequency of letters in any input text and compares it with standard frequencies in multiple languages. The program:
- Takes text input from the user
- Counts how often each letter appears (case-insensitive)
- Compares frequencies with standard frequencies in:
  - English
  - French
  - German
  - Italian
  - Dutch
- Uses both letter frequency analysis and Levenshtein distance algorithm to identify the input language
- Identifies which language the text most closely matches

## Project Structure
The project follows a clean architecture with clear separation of concerns:

![UML Class Diagram](docs/class-diagram.puml)

### Packages
- `com.letterfrequency` - Main application entry point
- `com.letterfrequency.model` - Data models and enums
- `com.letterfrequency.service` - Core language detection services
- `com.letterfrequency.ui` - User interface components
- `com.letterfrequency.util` - Utility classes

## Features
- Multi-language support
- Case-insensitive letter counting
- Detailed frequency analysis
- Dual language detection methods:
  1. Letter frequency analysis with digraph support
  2. Levenshtein distance word matching with expanded word lists
- Confidence scoring with threshold warnings
- Comprehensive comparison tables

## How to Use
1. Make sure you have Java 11 or higher installed
2. Clone the repository
3. Build the project:
   ```bash
   javac src/main/java/com/letterfrequency/**/*.java
   ```
4. Run the program:
   ```bash
   java -cp src/main/java com.letterfrequency.Main
   ```
5. Enter your text when prompted (press Enter twice to finish)
6. View the analysis results

## Output Format
The program provides three main analysis sections:
1. Letter Frequency Analysis - Shows how often each letter appears in your text compared to standard frequencies in each supported language
2. Language Analysis (Frequency-based) - Shows which language your text most closely matches based on letter frequency patterns
3. Levenshtein Analysis - Shows language matches based on word similarity using the Levenshtein distance algorithm

## Technical Details
The program uses three sophisticated approaches to detect the input language:

1. **Letter Frequency Analysis**
   - Counts the frequency of each letter in the input text
   - Compares these frequencies with standard letter frequencies in supported languages
   - Uses Euclidean distance to measure similarity

2. **Digraph Analysis**
   - Analyzes frequencies of two-letter combinations
   - Compares with language-specific digraph patterns
   - Provides additional accuracy for language detection

3. **Levenshtein Distance Analysis**
   - Compares input words with common words from each language
   - Uses string similarity scoring
   - Helps identify languages through vocabulary patterns

The final language detection combines all three approaches with configurable weights and confidence thresholds.
