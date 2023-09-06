package com.alg.productmanager.objects.dtos;

import com.alg.productmanager.objects.Enums.AccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;

/**
 * Account registration dto
 */
@Getter
@ToString
@AllArgsConstructor
public class AccountDto {

	@NotEmpty
	private String username;

	@NotEmpty
	private String email;

	@NotEmpty
	private String password;

	@NotEmpty
	private AccountType role;
}
