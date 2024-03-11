package com.example.demo.enums;

import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum AccountType {

    USER,
    STATION;

    public static AccountType find(String type) {

        return Stream.of(AccountType.values())
                .filter(accountType -> accountType.name().equalsIgnoreCase(type))
                .findFirst()
                .orElse(null);
    }
}
