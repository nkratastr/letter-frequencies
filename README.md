# Letter Frequency Language Detector

A sophisticated Java program that detects the language of input text using multiple analysis methods. The program combines five different detection algorithms for highly accurate language identification.

## Supported Languages
- English
- French
- German
- Italian
- Dutch

## Detection Methods

The program uses **five independent analysis methods** combined with dynamic weighting:

1. **Letter Frequency Analysis** - Compares letter distributions using Euclidean distance
2. **N-gram Analysis** - Analyzes character bigrams (2-letter sequences) characteristic of each language
3. **Stopword Analysis** - Exact matching of common stopwords (100+ words per language)
4. **Index of Coincidence (IC)** - Statistical measure of letter repetition probability
5. **Levenshtein Distance** - Fuzzy word matching against common vocabulary

## Project Structure

```
src/main/java/com/letterfrequency/
├── Main.java                    # Application entry point
├── model/
│   ├── Language.java            # Language enum
│   ├── LanguageCommonWords.java # Common words database
│   └── LanguageFrequencyAnalyzer.java
├── service/
│   ├── LanguageDetectionService.java      # Service interface
│   ├── CombinedLanguageDetectionService.java  # Main detection orchestrator
│   ├── FrequencyAnalysisService.java      # Letter frequency analysis
│   ├── NgramAnalysisService.java          # Bigram analysis
│   ├── StopwordAnalysisService.java       # Stopword matching
│   ├── IndexOfCoincidenceService.java     # IC calculation
│   └── LevenshteinAnalysisService.java    # Fuzzy word matching
├── ui/
│   └── ConsoleUI.java           # Console interface
└── util/
    └── TextValidator.java       # Input validation
```

## Features

- **Multi-method detection** - Five independent algorithms for robust detection
- **Dynamic weighting** - Weights adjust based on text length for optimal accuracy
- **Detailed analysis** - View individual scores from each detection method
- **Stopword counts** - See exact match counts per language
- **Letter frequency comparison** - Color-coded frequency table
- **Index of Coincidence** - Statistical language fingerprint

## How to Use

### Prerequisites
- Java 11 or higher

### Build and Run

```powershell
# Navigate to project directory
cd letter-frequencies

# Compile all Java files
$files = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }
javac -d out $files

# Run the program
java -cp out com.letterfrequency.Main
```

Or as a single command:
```powershell
$files = Get-ChildItem -Path src -Recurse -Filter *.java | ForEach-Object { $_.FullName }; javac -d out $files; java -cp out com.letterfrequency.Main
```

### Usage
1. Run the program
2. Enter or paste your text
3. Press Enter twice to submit
4. View the comprehensive analysis results

## Example Output

### Language Detection Results
```
Text Statistics: 229 characters, 36 words
Calculated Index of Coincidence: 0.0630

Language Detection Results:
+---------------+-----------+-----------+-----------+-----------+-----------+-----------+
| Language      | Frequency |  N-grams  | Stopwords |    IC     |Levenshtein| Combined  |
+---------------+-----------+-----------+-----------+-----------+-----------+-----------+
| German        |    62.91% |    76.25% |   100.00% |    73.62% |    31.43% |    75.83%| *
| Dutch         |    35.00% |    60.08% |    20.00% |    66.42% |    20.00% |    39.99%|
| English       |    35.71% |    44.53% |    10.00% |    92.62% |    34.29% |    38.60%|
| French        |    30.94% |    41.01% |     0.00% |    70.42% |     5.71% |    27.58%|
| Italian       |     0.00% |    46.60% |     0.00% |    78.42% |     8.57% |    24.27%|
+---------------+-----------+-----------+-----------+-----------+-----------+-----------+
* = Best Match

Stopword Matches per Language:
  German: 10 matches
  Dutch: 2 matches
  English: 1 matches
  French: 0 matches
  Italian: 0 matches
```

### Column Explanations
| Column | Description |
|--------|-------------|
| Frequency | Letter frequency similarity (Euclidean distance) |
| N-grams | Bigram pattern similarity (cosine similarity) |
| Stopwords | Common word exact match ratio |
| IC | Index of Coincidence similarity |
| Levenshtein | Fuzzy word matching score |
| Combined | Weighted combination of all methods |

## Technical Details

### Dynamic Weighting System

Weights are adjusted based on text length for optimal accuracy:

| Text Length | Frequency | N-grams | Stopwords | IC | Levenshtein |
|-------------|-----------|---------|-----------|-----|-------------|
| < 50 chars  | 10% | 15% | 45% | 10% | 20% |
| < 200 chars | 15% | 20% | 35% | 15% | 15% |
| < 500 chars | 20% | 25% | 30% | 15% | 10% |
| ≥ 500 chars | 25% | 30% | 25% | 15% | 5% |

**Rationale:** Short texts benefit from word-based matching (stopwords, Levenshtein), while longer texts allow statistical methods (frequency, n-grams) to be more reliable.

### Index of Coincidence Values

Expected IC values for each language:
| Language | Expected IC |
|----------|-------------|
| English  | 0.0667 |
| French   | 0.0778 |
| German   | 0.0762 |
| Italian  | 0.0738 |
| Dutch    | 0.0798 |

## Contributing

Feel free to contribute to this project by:
1. Forking the repository
2. Creating a feature branch
3. Making your changes
4. Submitting a pull request

## License

This project is licensed under the MIT License - see the LICENSE file for details.
