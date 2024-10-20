package com.example.expensemanager.services;

import com.example.expensemanager.entity.Transaction;

import java.util.List;

public interface TransactionService{
    Boolean createTransaction(Transaction transaction);
    Object updateTransaction(Transaction transaction);
    Boolean deleteTransaction(Integer transactionId);
    List<Transaction>  getAllTransaction();
    Object getTransaction(Integer transactionId);
}
