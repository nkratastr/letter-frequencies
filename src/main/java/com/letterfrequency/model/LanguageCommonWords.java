package com.letterfrequency.model;

import java.util.*;

public class LanguageCommonWords {
    private static final Map<Language, List<String>> COMMON_WORDS = new HashMap<>();
    
    static {
        COMMON_WORDS.put(Language.ENGLISH, Arrays.asList(
            "the", "be", "to", "of", "and", "a", "in", "that", "have", "i",
            "it", "for", "not", "on", "with", "he", "as", "you", "do", "at",
            "this", "but", "his", "by", "from", "they", "we", "say", "her", "she",
            "or", "an", "will", "my", "one", "all", "would", "there", "their", "what"
        ));
        
        COMMON_WORDS.put(Language.FRENCH, Arrays.asList(
            "le", "la", "les", "de", "et", "être", "avoir", "que", "pour", "dans",
            "ce", "il", "qui", "ne", "sur", "se", "pas", "plus", "par", "je",
            "avec", "tout", "faire", "son", "mettre", "autre", "on", "mais", "nous", "comme",
            "ou", "si", "leur", "elle", "peu", "aussi", "mon", "puis", "donc", "votre"
        ));
        
        COMMON_WORDS.put(Language.GERMAN, Arrays.asList(
            "der", "die", "das", "und", "in", "sein", "zu", "haben", "mit", "für",
            "nicht", "ich", "auf", "sie", "es", "sich", "ein", "auch", "von", "so",
            "aber", "bei", "nur", "noch", "werden", "jetzt", "nach", "bis", "wenn", "oder",
            "aus", "durch", "schon", "dieser", "dann", "unter", "kann", "über", "mich", "ihm"
        ));
        
        COMMON_WORDS.put(Language.ITALIAN, Arrays.asList(
            "il", "di", "che", "è", "la", "in", "non", "un", "per", "sono",
            "ma", "come", "da", "ho", "ci", "questo", "qui", "chi", "mi", "ha",
            "lei", "si", "lo", "dove", "nella", "sua", "cosa", "tu", "quando", "più",
            "anche", "gli", "dei", "delle", "della", "tutto", "fare", "tra", "essere", "molto"
        ));
        
        COMMON_WORDS.put(Language.DUTCH, Arrays.asList(
            "de", "het", "een", "van", "en", "in", "is", "dat", "op", "te",
            "zijn", "voor", "niet", "met", "hij", "ik", "je", "zij", "maar", "er",
            "dan", "om", "aan", "nog", "bij", "door", "na", "naar", "uit", "wie",
            "of", "zal", "waar", "was", "wel", "nu", "geen", "zou", "heeft", "meer"
        ));
    }
    
    public static Map<Language, List<String>> getCommonWords() {
        return Collections.unmodifiableMap(COMMON_WORDS);
    }
}
