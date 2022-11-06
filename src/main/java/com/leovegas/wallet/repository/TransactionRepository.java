package com.leovegas.wallet.repository;

import com.leovegas.wallet.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
