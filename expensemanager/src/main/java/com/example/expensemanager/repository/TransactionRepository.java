package com.example.expensemanager.repository;

import com.example.expensemanager.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Integer>{
}
