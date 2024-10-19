package com.example.expensemanager.services.serviceImpl;

import com.example.expensemanager.entity.Transaction;
import com.example.expensemanager.repository.TransactionRepository;
import com.example.expensemanager.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TransactionServiceImpl  implements TransactionService {

    TransactionRepository transactionRepository;
    TransactionServiceImpl (TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Boolean createTransaction(Transaction transaction) {
        if(!ObjectUtils.isEmpty(transaction)){
           Transaction t = transactionRepository.save(transaction);
           return true;
        }
        else{
            return false;
        }
    }

    @Override
    public Object updateTransaction(Transaction transaction) {
        if (!ObjectUtils.isEmpty(transaction)){
            Transaction savedTransaction = transactionRepository.getReferenceById(transaction.getTransactionId());
            savedTransaction.setName(transaction.getName());
            savedTransaction.setCategory(transaction.getCategory());
            savedTransaction.setDescription(transaction.getDescription());
            savedTransaction.setAmount(transaction.getAmount());
            savedTransaction.setIsDeleted(transaction.getIsDeleted());
            return transactionRepository.save(savedTransaction);

        }
        else {
            return new RuntimeException( transaction.getName() + " not Found");
        }
    }

    @Override
    public Boolean deleteTransaction(Integer transactionId) {
        return null;
    }

    @Override
    public List<Object> getAllTransaction() {
        return List.of();
    }

    @Override
    public Transaction getTransaction(Integer transactionId) {
       return transactionRepository.getReferenceById(transactionId);
    }
}
