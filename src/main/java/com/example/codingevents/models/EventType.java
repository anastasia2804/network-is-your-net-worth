package com.example.codingevents.models;

public enum EventType {

    CONFERENCE("Coffee"),
    MEETUP("Tea"),
    WORKSHOP("Boba"),
    SOCIAL("Water");

    private final String displayName;

    EventType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
