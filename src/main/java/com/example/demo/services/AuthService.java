package com.example.demo.services;

import com.example.demo.models.AccountDto;

public interface AuthService {

    String register(AccountDto registerAccountDto);

    String authorise(AccountDto authoriseAccountDto);
}
