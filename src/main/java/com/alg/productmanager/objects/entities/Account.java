package com.alg.productmanager.objects.entities;

import com.alg.productmanager.objects.Enums.AccountType;
import jakarta.persistence.*;
import lombok.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name="accounts")
public class Account {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	private String username;

	private String email;

	private String password;

	private AccountType role;
}
