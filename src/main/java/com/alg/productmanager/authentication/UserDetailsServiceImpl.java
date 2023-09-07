package com.alg.productmanager.authentication;

import com.alg.productmanager.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailsServiceImpl implements UserDetailsService {
    private @Autowired AccountRepository accountRepository;

    @Override
    public CustomUserDetailsImpl loadUserByUsername(String username)
            throws UsernameNotFoundException {
        var user = accountRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Could not find account");
        }

        return new CustomUserDetailsImpl(user);
    }
}
