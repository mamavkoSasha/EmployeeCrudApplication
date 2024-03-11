package com.example.demo.models;

import com.example.demo.enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private String email;
    private String username;
    private char[] password;
    private AccountType accountType = AccountType.USER;
}
