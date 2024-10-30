package com.example.expensemanager.controller;

import com.example.expensemanager.dto.ApiResponse;
import com.example.expensemanager.dto.TransactionDTO;
import com.example.expensemanager.entity.Transaction;
import com.example.expensemanager.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController{

    private TransactionService transactionService;

    //logging slf4j object
    static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    public TransactionController ( TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/createExpense/")
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionDTO transactionDto){
        Boolean isCreated =  this.transactionService.createTransaction(transactionDto);
        if(isCreated) {
            logger.info("transaction created successfully");
            return new ResponseEntity(new ApiResponse("Transaction saved successfully", isCreated), HttpStatus.CREATED);
        }
        else{
            logger.info("transaction failed to create");
            return new ResponseEntity<>(new ApiResponse("Failed to save transaction",isCreated),HttpStatus.BAD_REQUEST);
        }
    }

     @GetMapping("/{transactionId}")
    public ResponseEntity<Object> getTransactionById(@PathVariable Integer transactionId) {
         Object transaction = this.transactionService.getTransaction(transactionId);
         if (!ObjectUtils.isEmpty(transaction)){
             logger.info("transaction fetched successfully");
             return new ResponseEntity<>((Transaction)transaction, HttpStatus.OK);
            }
        else{
             logger.info("transaction failed to fetch");
           return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
    }
    ///Get transaction by the userId depending which user is login in
    @GetMapping("/user/{userId}")
    public ResponseEntity<Object> getTransaction(@PathVariable Integer userId) {
        List<Transaction> transactionList = this.transactionService.getAllTransaction(userId);
        if (!ObjectUtils.isEmpty(transactionList)){
            logger.info("transaction list fetched  successfully");
            return new ResponseEntity<>(transactionList, HttpStatus.OK);
        }
        else{
            logger.info("transaction list failed to fetched");
            return new ResponseEntity<>(new ApiResponse("Fail to get all list",false),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("update/")
    public ResponseEntity<Object> updateTransaction(@RequestBody TransactionDTO transactionDto){
        Object savedTran = transactionService.updateTransaction(transactionDto);
        logger.info("transaction updated successfully");
        return new ResponseEntity(savedTran,HttpStatus.ACCEPTED);
    }

    @DeleteMapping("delete/{transactionId}")
    public ResponseEntity<ApiResponse> deleteTransaction(@PathVariable Integer transactionId){
        Boolean isDeleted = transactionService.deleteTransaction(transactionId);
        if(isDeleted){
            logger.info("transaction deleted successfully");
            return new ResponseEntity<>(new ApiResponse("Transaction deleted Successfully",isDeleted),HttpStatus.ACCEPTED);
        }
        else{
            logger.info("transaction failed to delete ");
            return new ResponseEntity<>(new ApiResponse("Transaction failed to deleted",isDeleted),HttpStatus.ACCEPTED);
        }
    }

}
