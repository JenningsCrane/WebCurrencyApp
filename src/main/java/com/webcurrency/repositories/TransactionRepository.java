package com.webcurrency.repositories;

import com.webcurrency.models.account.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
