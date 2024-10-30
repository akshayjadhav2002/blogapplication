package com.example.expensemanager.services;

import com.example.expensemanager.dto.TransactionDTO;
import com.example.expensemanager.entity.Transaction;

import java.util.List;

public interface TransactionService{
    Boolean createTransaction(TransactionDTO transactionDTO);
    Object updateTransaction(TransactionDTO transactionDTO);
    Boolean deleteTransaction(Integer transactionId);
    List<Transaction>  getAllTransaction(Integer userId);
    Object getTransaction(Integer transactionId);
}
