package com.alg.productmanager.objects.Enums;

import lombok.Getter;

@Getter
public enum AccountType {
    USER("USER"),
    ADMIN("ADMIN");

    private final String literal;

    AccountType(String literal) {
        this.literal = literal;
    }

    public String getLiteral() {
        return literal;
    }
}
