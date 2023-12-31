package com.alg.productmanager.objects.dtos;

import com.alg.productmanager.objects.Enums.AccountType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

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

	private AccountType role;
}
