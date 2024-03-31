package com.webcurrency.repositories;

import com.webcurrency.models.account.Transaction;
import com.webcurrency.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    public List<Transaction> findAllByUser(User user);
}
