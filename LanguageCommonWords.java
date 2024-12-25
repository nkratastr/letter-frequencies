import java.util.*;

public class LanguageCommonWords {
    private static final Map<Language, List<String>> COMMON_WORDS = new HashMap<>();
    
    static {
        COMMON_WORDS.put(Language.ENGLISH, Arrays.asList(
            "the", "be", "to", "of", "and", "a", "in", "that", "have", "i"
        ));
        
        COMMON_WORDS.put(Language.FRENCH, Arrays.asList(
            "le", "la", "les", "de", "et", "être", "avoir", "que", "pour", "dans"
        ));
        
        COMMON_WORDS.put(Language.GERMAN, Arrays.asList(
            "der", "die", "das", "und", "in", "sein", "zu", "haben", "mit", "für"
        ));
        
        COMMON_WORDS.put(Language.ITALIAN, Arrays.asList(
            "il", "di", "che", "è", "la", "in", "non", "un", "per", "sono"
        ));
        
        COMMON_WORDS.put(Language.DUTCH, Arrays.asList(
            "de", "het", "een", "van", "en", "in", "is", "dat", "op", "te"
        ));
    }
    
    public static Map<Language, List<String>> getCommonWords() {
        return Collections.unmodifiableMap(COMMON_WORDS);
    }
}
