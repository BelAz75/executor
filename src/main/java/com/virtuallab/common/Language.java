package com.virtuallab.common;

public enum Language {
    JAVA(".java"),
    PYTHON(".py"),
    C(".c"),
    PASCAL(".pas");

    private final String extension;

    Language(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public static Language toEnum(String language) {
        switch (language) {
            case "java":
                return JAVA;
            case "python":
                return PYTHON;
            case "c":
                return C;
            case "pascal":
                return PASCAL;
            default:
                throw new RuntimeException("Programming language " + language + " not supported");
        }
    }
}
