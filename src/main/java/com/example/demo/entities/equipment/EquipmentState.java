package com.example.demo.entities.equipment;

import java.util.HashMap;
import java.util.Map;

public enum EquipmentState {
    PLANNED("Projektiert"),
    REQUESTED("Angefordert"),
    ORDERED("Bestellt"),
    DELIVERED("Geliefert"),
    IN_USE("Eingesetzt"),
    ON_LOAN("Ausgeliehen"),
    IN_REPAIR("Reparatur"),
    RETIRED("Ausgemustert");

    EquipmentState (String name) {
        this.name = name;
    }

    private final String name;
    private static final Map<String, EquipmentState> BY_NAME = new HashMap<>();

    @Override
    public String toString() {
        return name;
    }

    static {
        for (EquipmentState status : values()) {
            BY_NAME.put(status.name(), status);
        }
    }

    public static EquipmentState fromName(String name) {
        return BY_NAME.get(name);
    }
}
