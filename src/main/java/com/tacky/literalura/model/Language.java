package com.tacky.literalura.model;

public enum Language {
    English("en", "Ingles"),
    Spanish("fr", "Frances"),
    French("es", "Espanol");

    private String languageOmdb;
    private String languageSpanish;

    Language(String languageOmdb, String languageSpanish) {
        this.languageOmdb = languageOmdb;
        this.languageSpanish = languageSpanish;
    }

    public static Language fromString(String text) {
        for (Language language : Language.values()) {
            if (language.languageOmdb.equalsIgnoreCase(text)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Ninguna lenguaje encontrada: " + text);
    }

    public static Language fromEspanol(String text) {
        for (Language language : Language.values()) {
            if (language.languageSpanish.equalsIgnoreCase(text)) {
                return language;
            }
        }
        throw new IllegalArgumentException("Ninguna lenguaje encontrada: " + text);
    }
}
