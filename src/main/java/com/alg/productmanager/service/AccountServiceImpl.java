package com.alg.productmanager.service;

import com.alg.productmanager.converters.AccountConverter;
import com.alg.productmanager.exceptions.AccountExistsException;
import com.alg.productmanager.exceptions.GenericError;
import com.alg.productmanager.objects.dtos.AccountDto;
import com.alg.productmanager.objects.entities.Account;
import com.alg.productmanager.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;
  private final AccountConverter accountConverter;

  @Override
  public Account accountRegistration(AccountDto dto) throws AccountExistsException {

    var existingAccount = accountRepository.findByUsername(dto.getUsername());

    if (existingAccount != null) {
      throw new AccountExistsException(GenericError.ACCOUNT_WITH_SAME_USERNAME_EXISTS, dto.getUsername());
    }
    var newAccount = accountConverter.toAccount(dto);
    newAccount = accountRepository.save(newAccount);

    return newAccount;
  }
}
