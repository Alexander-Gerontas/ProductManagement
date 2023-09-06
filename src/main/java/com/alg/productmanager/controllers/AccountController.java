package com.alg.productmanager.controllers;

import com.alg.productmanager.exceptions.AccountExistsException;
import com.alg.productmanager.objects.dtos.AccountDto;
import com.alg.productmanager.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/account")
public class AccountController {

  private final AccountService accountService;

  @PostMapping(value = "/registration", produces = "application/json")
  public ResponseEntity<String> registration(
          @Valid @RequestBody AccountDto accountDto) throws AccountExistsException {

    log.info("Registering account with username: " + accountDto.getUsername());

    try {
      var account = accountService.accountRegistration(accountDto);
      return ResponseEntity.ok().body("account with username: "
              + account.getUsername() + " and role: " + account.getRole() + " created");
    } catch (AccountExistsException e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }
}

