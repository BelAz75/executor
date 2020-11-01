package com.virtuallab.task;

public class TypeMapper {
    public static String getTypeForLanguage(String language, String type) {
        switch (language) {
            case "java":
                switch (type) {
                    case "INTEGER":
                        return "int";
                    case "STRING":
                    default:
                        return "String";
                }
        }
        return "String";
    }
}
