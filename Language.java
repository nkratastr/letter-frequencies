public enum Language {
    ENGLISH("English"),
    DUTCH("Dutch"),
    FRENCH("French"),
    ITALIAN("Italian"),
    GERMAN("German")

    private final String displayName;

    Language(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
