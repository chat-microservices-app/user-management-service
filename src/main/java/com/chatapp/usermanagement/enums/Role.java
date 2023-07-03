package com.chatapp.usermanagement.enums;

public enum Role {

    PREFIX("ROLE_");

    private final String label;

    Role(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
