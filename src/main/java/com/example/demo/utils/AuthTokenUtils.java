package com.example.demo.utils;

import com.example.demo.enums.AccountType;
import com.example.demo.models.ValidationResult;

public interface AuthTokenUtils {

    String generateToken(AccountType accountType, String accountId);

    ValidationResult validateToken(String token);
}

