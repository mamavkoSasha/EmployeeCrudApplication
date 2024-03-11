package com.example.demo.models;

import com.example.demo.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResult {

    private boolean isTokenValid;
    private String accountId;
    private AccountType accountType;
}
