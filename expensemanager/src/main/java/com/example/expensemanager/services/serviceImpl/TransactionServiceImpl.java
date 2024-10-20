package com.example.expensemanager.services.serviceImpl;

import com.example.expensemanager.entity.ApiResponse;
import com.example.expensemanager.entity.Transaction;
import com.example.expensemanager.repository.TransactionRepository;
import com.example.expensemanager.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TransactionServiceImpl  implements TransactionService {

    final static Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    TransactionRepository transactionRepository;
    TransactionServiceImpl (TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    @Override
    public Boolean createTransaction(Transaction transaction) {
        if(!ObjectUtils.isEmpty(transaction)){
           Transaction savedTransaction = transactionRepository.save(transaction);
           logger.info("Transaction saved - {}",savedTransaction);
           return true;
        }
        else{
            logger.info("fail to save the transaction");
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
            logger.info("Transaction update  successfully - {}",savedTransaction);
            return transactionRepository.save(savedTransaction);

        }
        else {
            logger.info("Transaction update failed");
            return new ApiResponse( transaction.getName() + " not Found",false);
        }
    }

    @Override
    public Boolean deleteTransaction(Integer transactionId) {
        Transaction transaction = transactionRepository.getReferenceById(transactionId);
        if(!ObjectUtils.isEmpty(transaction)){
            transactionRepository.delete(transaction);
            logger.info("Transaction deleted successfully - {}",transaction);
            return  true;
        }
        else{
            logger.info("Transaction failed to delete");
            return false;
        }

    }

    @Override
    public List<Transaction> getAllTransaction() {
        List<Transaction> transactionList = transactionRepository.findAll();
        logger.info("All transaction list fetched");
        return transactionList;
    }

    @Override
    public Object getTransaction(Integer transactionId) {
        if(transactionId<0){
            logger.info("Invalid transaction id received");
            return new ApiResponse("Invalid transaction Id",false);
        }
        else{
            Transaction transaction = transactionRepository.getReferenceById(transactionId);
            logger.info("Transaction fetched from Database successfully - {}",transaction);
            return transaction;
        }

    }
}
