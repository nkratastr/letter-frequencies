package com.letterfrequency.service;

import com.letterfrequency.model.Language;
import java.util.Map;

public interface LanguageDetectionService {
    Language detectLanguage(String text);
    Map<Language, Double> getLanguageScores(String text);
}
