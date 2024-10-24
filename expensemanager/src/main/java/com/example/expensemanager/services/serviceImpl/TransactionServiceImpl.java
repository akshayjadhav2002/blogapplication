package com.example.expensemanager.services.serviceImpl;

import com.example.expensemanager.dto.ApiResponse;
import com.example.expensemanager.dto.TransactionDTO;
import com.example.expensemanager.entity.Transaction;
import com.example.expensemanager.repository.TransactionRepository;
import com.example.expensemanager.services.TransactionService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class TransactionServiceImpl  implements TransactionService {

    final static Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);
    private final ModelMapper modelMapper;

    TransactionRepository transactionRepository;
    TransactionServiceImpl (TransactionRepository transactionRepository, ModelMapper modelMapper){
        this.transactionRepository = transactionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean createTransaction(TransactionDTO transactionDTO) {
        if(!ObjectUtils.isEmpty(transactionDTO)){
            Transaction transaction = modelMapper.map(transactionDTO,Transaction.class);
           Transaction savedTransaction = transactionRepository.save(transaction);
           logger.info("Transaction saved in database - {}",savedTransaction);
           return true;
        }
        else{
            logger.info("fail to save the transaction");
            return false;
        }
    }

    @Override
    public Object updateTransaction(TransactionDTO transactionDTO) {
        if (!ObjectUtils.isEmpty(transactionDTO)){
            Transaction savedTransaction = transactionRepository.getReferenceById(transactionDTO.getTransactionId());
            savedTransaction.setName(transactionDTO.getName());
            savedTransaction.setDescription(transactionDTO.getDescription());
            savedTransaction.setAmount(transactionDTO.getAmount());
            savedTransaction.setDateTime(transactionDTO.getDateTime());
//            savedTransaction.setUser(transactionDTO.getUserId());
//            savedTransaction.setCategory(transactionDTO.getCategoryId());
            ///we will edit it after seeing data base updates and the category and user updates
            logger.info("Transaction update  successfully - {}",savedTransaction);
            return transactionRepository.save(savedTransaction);

        }
        else {
            logger.info("Transaction update failed");
            return new ApiResponse( transactionDTO.getName() + " not Found",false);
        }
    }

    @Override
    public Boolean deleteTransaction(Integer transactionId) {
        Transaction transaction = transactionRepository.getReferenceById(transactionId);
        if(!ObjectUtils.isEmpty(transaction)){
            try {
                transactionRepository.delete(transaction);
                logger.info("Transaction deleted successfully - {}", transaction);
                return true;
            }
            catch (Exception e){
                throw new RuntimeException("Transaction does not Exists in Database -"+ e.getMessage());
            }
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
