package com.alg.productmanager.converters;

import com.alg.productmanager.objects.dtos.AccountDto;
import com.alg.productmanager.objects.entities.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Converts Account DTOs to domain objects.
 */
@Component
@RequiredArgsConstructor
public class AccountConverter {

    private final PasswordEncoder passwordEncoder;

    public Account toAccount(AccountDto accountDto) {

        var account = new Account();

        account.setUsername(accountDto.getUsername());
        account.setEmail(accountDto.getEmail());

        account.setPassword(passwordEncoder.encode(accountDto.getPassword()));

        account.setRole(accountDto.getRole());

        return account;
    }
}
