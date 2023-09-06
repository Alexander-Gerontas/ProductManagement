package com.alg.productmanager.utils;

import com.alg.productmanager.objects.Enums.AccountType;
import com.alg.productmanager.objects.dtos.AccountDto;

public final class AccountDtoFactory {
    public static AccountDto getSimpleAccountrDto() {

        var userDto = new AccountDto(
                "simpleUser",
                "abc@mail.com",
                "userpass",
                AccountType.USER
        );

        return userDto;
    }

    public static AccountDto getAdminUserDto() {

        var accountDto = new AccountDto(
                "adminUser",
                "efg@mail.com",
                "adminpass",
                AccountType.ADMIN
        );

        return accountDto;
    }
}
