package com.example.demo.model;

public enum Category {
    PHONE("Điện thoại"),
    LAPTOP("Laptop");

    private final String displayName;

    Category(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
