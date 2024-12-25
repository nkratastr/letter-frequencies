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

## Features
- Multi-language support
- Case-insensitive letter counting
- Detailed frequency analysis
- Dual language detection methods:
  1. Letter frequency analysis
  2. Levenshtein distance word matching
- Language matching
- Comprehensive comparison tables

## How to Use
1. Compile all required files:
   ```bash
   javac LetterFrequency.java LevenshteinAnalyzer.java LanguageCommonWords.java Language.java TextValidator.java LanguageFrequencyAnalyzer.java
   ```
2. Run the program: `java LetterFrequency`
3. Enter your text when prompted (press Enter twice to finish)
4. View the analysis results:
   - Letter frequency table comparing with all supported languages
   - Language analysis based on letter frequencies
   - Word analysis using Levenshtein distance algorithm

## Output Format
The program provides three main analysis sections:
1. Letter Frequency Analysis - Shows how often each letter appears in your text compared to standard frequencies in each supported language
2. Language Analysis (Frequency-based) - Shows which language your text most closely matches based on letter frequency patterns
3. Levenshtein Analysis - Shows language matches based on word similarity using the Levenshtein distance algorithm

## Technical Details
The program uses two different approaches to detect the input language:

1. **Letter Frequency Analysis**
   - Counts the frequency of each letter in the input text
   - Compares these frequencies with standard letter frequencies in supported languages
   - Calculates the total difference to find the best matching language

2. **Levenshtein Distance Analysis**
   - Analyzes individual words (3 or more characters)
   - Compares each word with common words from supported languages
   - Uses Levenshtein distance algorithm to find the closest matching words
   - Aggregates word matches to determine the most likely language
