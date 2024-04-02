package com.webcurrency.services;

import com.webcurrency.exceptions.UserNotCreatedException;
import com.webcurrency.models.account.Account;
import com.webcurrency.repositories.AccountRepository;
import com.webcurrency.utils.AccountCreator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.webcurrency.models.user.Role;
import com.webcurrency.models.user.User;
import com.webcurrency.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final AccountRepository accountRepository;

    public User save(User user) {
        return repository.saveAndFlush(user);
    }

    public void create(User user) {
        if (repository.existsByEmail(user.getEmail())) {
            throw new UserNotCreatedException("Пользователь с таким email уже существует");
        }

        if (repository.existsByPhoneNumber(user.getPhoneNumber())) {
            throw new UserNotCreatedException("Пользователь с таким номером телефона уже существует");
        }

        User savedUser = save(user);
        createAccountsForNewUser(savedUser);
    }

    private void createAccountsForNewUser(User user) {
        List<Account> accounts = AccountCreator.createAccountsForNewUser(user);
        accountRepository.saveAll(accounts);
    }

    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }

    @Deprecated
    public void getAdmin() {
        User user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }

    public User getById(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}