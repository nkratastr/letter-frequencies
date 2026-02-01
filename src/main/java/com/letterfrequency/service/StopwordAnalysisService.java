package com.letterfrequency.service;

import com.letterfrequency.model.Language;
import java.util.*;

/**
 * Language detection service based on exact stopword matching.
 * Stopwords are the most common words in a language that appear frequently in any text.
 * Exact matching of these words is a reliable indicator of language.
 */
public class StopwordAnalysisService implements LanguageDetectionService {
    
    private final Map<Language, Set<String>> stopwords;
    
    public StopwordAnalysisService() {
        this.stopwords = initializeStopwords();
    }
    
    @Override
    public Language detectLanguage(String text) {
        Map<Language, Double> scores = getLanguageScores(text);
        return scores.entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElse(Language.ENGLISH);
    }
    
    @Override
    public Map<Language, Double> getLanguageScores(String text) {
        // Extract words from text (only basic Latin letters)
        String[] words = text.toLowerCase()
            .replaceAll("[^a-zA-Z]", " ")
            .split("\\s+");
        
        Map<Language, Integer> matchCounts = new EnumMap<>(Language.class);
        
        // Initialize all languages with 0
        for (Language lang : Language.values()) {
            matchCounts.put(lang, 0);
        }
        
        // Count stopword matches for each language
        for (String word : words) {
            if (word.length() >= 2) {
                for (Language lang : Language.values()) {
                    if (stopwords.get(lang).contains(word)) {
                        matchCounts.merge(lang, 1, Integer::sum);
                    }
                }
            }
        }
        
        // Normalize scores
        Map<Language, Double> scores = new EnumMap<>(Language.class);
        int maxCount = matchCounts.values().stream().mapToInt(Integer::intValue).max().orElse(1);
        
        if (maxCount == 0) {
            // No matches found, return equal scores
            for (Language lang : Language.values()) {
                scores.put(lang, 0.2);
            }
        } else {
            for (Language lang : Language.values()) {
                scores.put(lang, (double) matchCounts.get(lang) / maxCount);
            }
        }
        
        return scores;
    }
    
    /**
     * Get the count of stopword matches for each language.
     */
    public Map<Language, Integer> getStopwordMatchCounts(String text) {
        String[] words = text.toLowerCase()
            .replaceAll("[^a-zA-Z]", " ")
            .split("\\s+");
        
        Map<Language, Integer> matchCounts = new EnumMap<>(Language.class);
        for (Language lang : Language.values()) {
            matchCounts.put(lang, 0);
        }
        
        for (String word : words) {
            if (word.length() >= 2) {
                for (Language lang : Language.values()) {
                    if (stopwords.get(lang).contains(word)) {
                        matchCounts.merge(lang, 1, Integer::sum);
                    }
                }
            }
        }
        
        return matchCounts;
    }
    
    /**
     * Initialize stopwords for each language - comprehensive lists for better detection.
     */
    private Map<Language, Set<String>> initializeStopwords() {
        Map<Language, Set<String>> words = new EnumMap<>(Language.class);
        
        // English stopwords
        words.put(Language.ENGLISH, new HashSet<>(Arrays.asList(
            "the", "be", "to", "of", "and", "a", "in", "that", "have", "i",
            "it", "for", "not", "on", "with", "he", "as", "you", "do", "at",
            "this", "but", "his", "by", "from", "they", "we", "say", "her", "she",
            "or", "an", "will", "my", "one", "all", "would", "there", "their", "what",
            "so", "up", "out", "if", "about", "who", "get", "which", "go", "me",
            "when", "make", "can", "like", "time", "no", "just", "him", "know", "take",
            "people", "into", "year", "your", "good", "some", "could", "them", "see", "other",
            "than", "then", "now", "look", "only", "come", "its", "over", "think", "also",
            "back", "after", "use", "two", "how", "our", "work", "first", "well", "way",
            "even", "new", "want", "because", "any", "these", "give", "day", "most", "us",
            "is", "are", "was", "were", "been", "being", "has", "had", "does", "did",
            "shall", "should", "may", "might", "must", "am", "very", "here", "where", "why"
        )));
        
        // Dutch stopwords
        words.put(Language.DUTCH, new HashSet<>(Arrays.asList(
            "de", "het", "een", "van", "en", "in", "is", "dat", "op", "te",
            "zijn", "voor", "niet", "met", "hij", "ik", "je", "zij", "maar", "er",
            "dan", "om", "aan", "nog", "bij", "door", "na", "naar", "uit", "wie",
            "of", "zal", "waar", "was", "wel", "nu", "geen", "zou", "heeft", "meer",
            "worden", "kan", "ook", "als", "dit", "tot", "wat", "hebben", "mijn", "over",
            "deze", "hun", "werd", "haar", "zo", "worden", "wij", "al", "waren", "veel",
            "me", "ben", "tegen", "men", "hem", "iets", "toch", "reeds", "hier", "ons",
            "die", "der", "zelf", "alleen", "ander", "andere", "onder", "zonder", "echter", "dus",
            "eens", "heel", "ieder", "alle", "omdat", "wanneer", "zeer", "altijd", "misschien", "eigen",
            "jullie", "moeten", "mogen", "willen", "kunnen", "zullen", "gaan", "komen", "doen", "maken"
        )));
        
        // French stopwords
        words.put(Language.FRENCH, new HashSet<>(Arrays.asList(
            "le", "la", "les", "de", "et", "un", "une", "que", "pour", "dans",
            "ce", "il", "qui", "ne", "sur", "se", "pas", "plus", "par", "je",
            "avec", "tout", "faire", "son", "mettre", "autre", "on", "mais", "nous", "comme",
            "ou", "si", "leur", "elle", "peu", "aussi", "mon", "puis", "donc", "votre",
            "au", "aux", "du", "des", "est", "sont", "ont", "ete", "cette", "ces",
            "vous", "tu", "ton", "ta", "tes", "mes", "nos", "vos", "ses", "lui",
            "eux", "cela", "ceci", "ci", "ici", "moi", "toi", "soi", "quel", "quelle",
            "quoi", "dont", "peut", "fait", "bien", "meme", "entre", "apres", "avant", "sous",
            "sans", "chez", "vers", "jusque", "depuis", "pendant", "selon", "contre", "tres", "encore",
            "avoir", "etre", "dit", "dit", "quand", "comment", "pourquoi", "toujours", "jamais", "rien"
        )));
        
        // German stopwords
        words.put(Language.GERMAN, new HashSet<>(Arrays.asList(
            "der", "die", "das", "und", "in", "sein", "zu", "haben", "mit", "fur",
            "nicht", "ich", "auf", "sie", "es", "sich", "ein", "auch", "von", "so",
            "aber", "bei", "nur", "noch", "werden", "jetzt", "nach", "bis", "wenn", "oder",
            "aus", "durch", "schon", "dieser", "dann", "unter", "kann", "uber", "mich", "ihm",
            "den", "dem", "des", "einer", "einem", "einen", "eines", "ist", "sind", "war",
            "waren", "wird", "wir", "ihr", "ihre", "ihrem", "ihren", "ihrer", "als", "wie",
            "man", "doch", "weil", "was", "hier", "da", "wo", "wer", "wen", "wem",
            "sein", "seine", "seinem", "seinen", "seiner", "mein", "meine", "meinem", "meinen", "meiner",
            "dein", "deine", "deinem", "deinen", "deiner", "uns", "euch", "diese", "dieses", "diesem",
            "diesen", "viel", "mehr", "sehr", "kein", "keine", "keinem", "keinen", "keiner", "selbst"
        )));
        
        // Italian stopwords
        words.put(Language.ITALIAN, new HashSet<>(Arrays.asList(
            "il", "di", "che", "la", "in", "non", "un", "per", "sono", "una",
            "ma", "come", "da", "ho", "ci", "questo", "qui", "chi", "mi", "ha",
            "lei", "si", "lo", "dove", "nella", "sua", "cosa", "tu", "quando", "piu",
            "anche", "gli", "dei", "delle", "della", "tutto", "fare", "tra", "essere", "molto",
            "del", "al", "dal", "nel", "sul", "con", "alla", "dalla", "nella", "sulla",
            "ai", "dai", "nei", "sui", "alle", "dalle", "nelle", "sulle", "gli", "le",
            "io", "noi", "voi", "loro", "lui", "essa", "esso", "esse", "essi", "mio",
            "mia", "miei", "mie", "tuo", "tua", "tuoi", "tue", "suo", "suoi", "sue",
            "nostro", "nostra", "nostri", "nostre", "vostro", "vostra", "vostri", "vostre", "questo", "questa",
            "questi", "queste", "quello", "quella", "quelli", "quelle", "stato", "stata", "stati", "state"
        )));
        
        return words;
    }
}
