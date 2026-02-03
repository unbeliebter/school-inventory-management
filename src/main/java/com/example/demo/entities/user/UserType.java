package com.example.demo.entities.user;

import java.util.HashMap;
import java.util.Map;

public enum UserType {
    ADMIN("Administrator"),
    RESPONSIBLE("Verantwortlicher"),
    TEACHER("Lehrer"),
    USER("Benutzer");

    private final String label; // Umbenannt, um Konflikt mit name() zu vermeiden
    private static final Map<String, UserType> BY_LABEL = new HashMap<>();

    UserType(String label) {
        this.label = label;
    }

    static {
        for (UserType type : values()) {
            BY_LABEL.put(type.label, type); // Hier nutzen wir den deutschen Namen!
        }
    }

    public static UserType fromLabel(String label) {
        return BY_LABEL.get(label);
    }
}