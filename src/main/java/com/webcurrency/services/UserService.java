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

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final AccountRepository accountRepository;

    /**
     * Сохранение пользователя
     *
     * @return сохраненный пользователь
     */
    public User save(User user) {
        return repository.saveAndFlush(user);
    }


    /**
     * Создание пользователя
     *
     * @return созданный пользователь
     */
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


    /**
     * Получение пользователя по имени пользователя
     *
     * @return пользователь
     */
    public User getByUsername(String username) {
        return repository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

    }

    /**
     * Получение пользователя по имени пользователя
     * <p>
     * Нужен для Spring Security
     *
     * @return пользователь
     */
    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    /**
     * Получение текущего пользователя
     *
     * @return текущий пользователь
     */
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }


    /**
     * Выдача прав администратора текущему пользователю
     * <p>
     * Нужен для демонстрации
     */
    @Deprecated
    public void getAdmin() {
        User user = getCurrentUser();
        user.setRole(Role.ROLE_ADMIN);
        save(user);
    }

    public User getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }
}