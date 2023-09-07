package com.alg.productmanager.service;

import com.alg.productmanager.exceptions.AccountExistsException;
import com.alg.productmanager.objects.dtos.AccountDto;
import com.alg.productmanager.objects.entities.Account;

public interface AccountService {

  /** creates a new account, throws exception if the user exists */
  Account accountRegistration(AccountDto dto) throws AccountExistsException;
}
