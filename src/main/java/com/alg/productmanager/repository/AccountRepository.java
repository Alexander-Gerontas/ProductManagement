package com.alg.productmanager.repository;

import com.alg.productmanager.objects.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findByUsername(String username);
}
