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
  1. Letter frequency analysis
  2. Levenshtein distance word matching
- Combined scoring system
- Color-coded comparison tables

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

## Example Output

### Language Detection Results
```
+---------------+-------------+-------------+-------------+
| Language      | Frequency  | Levenshtein | Combined   |
+---------------+-------------+-------------+-------------+
| English       |    85.23%  |    92.45%  |    88.12%  | (Best Match)
| Dutch         |    82.15%  |    78.32%  |    80.62%  |
| German        |    75.45%  |    82.18%  |    78.14%  |
| French        |    72.33%  |    68.92%  |    70.96%  |
| Italian       |    68.78%  |    65.45%  |    67.44%  |
+---------------+-------------+-------------+-------------+
```
The table shows:
- Frequency Score: How well the letter frequencies match each language
- Levenshtein Score: How well the common words match each language
- Combined Score: Weighted combination of both scores
Higher percentages indicate better matches.

### Letter Frequencies Comparison
```
+--------+----------+----------+----------+----------+----------+----------+
| Letter | Input    | English  | French   | German   | Italian  | Dutch    |
+--------+----------+----------+----------+----------+----------+----------+
| a      |    8.17% |    8.17% |    7.64% |    6.52% |   11.75% |    7.49% |
| b      |    1.50% |    1.50% |    0.90% |    1.89% |    0.92% |    1.58% |
| c      |    2.78% |    2.78% |    3.26% |    2.73% |    4.50% |    1.24% |
...etc...
+--------+----------+----------+----------+----------+----------+----------+
```
Color coding in the frequency table:
- Green: Very close match (difference < 0.5%)
- Yellow: Moderate match (difference < 1.0%)
- Red: Large difference (difference > 2.0%)

## Technical Details
The program uses two sophisticated approaches to detect the input language:

1. **Letter Frequency Analysis**
   - Counts the frequency of each letter in the input text
   - Compares these frequencies with standard letter frequencies in supported languages
   - Uses Euclidean distance to measure similarity

2. **Levenshtein Distance Analysis**
   - Compares input words with common words from each language
   - Uses string similarity scoring
   - Helps identify languages through vocabulary patterns

The final language detection combines both approaches with configurable weights:
- Frequency Analysis: 60% weight
- Levenshtein Analysis: 40% weight

## Contributing
Feel free to contribute to this project by:
1. Forking the repository
2. Creating a feature branch
3. Making your changes
4. Submitting a pull request

## License
This project is licensed under the MIT License - see the LICENSE file for details.
