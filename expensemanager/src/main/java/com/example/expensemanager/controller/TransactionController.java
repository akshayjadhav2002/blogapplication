package com.example.expensemanager.controller;

import com.example.expensemanager.entity.ApiResponse;
import com.example.expensemanager.entity.Transaction;
import com.example.expensemanager.services.TransactionService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController{

    private TransactionService transactionService;

    public TransactionController ( TransactionService transactionService){
        this.transactionService = transactionService;
    }

    @PostMapping("/createExpense/")
    public ResponseEntity<Object> createTransaction(@RequestBody Transaction transaction){
        Boolean isCreated =  this.transactionService.createTransaction(transaction);
        if(isCreated) {
            return new ResponseEntity(new ApiResponse("Transaction saved successfully", isCreated), HttpStatus.CREATED);
        }
        else{
            return new ResponseEntity<>(new ApiResponse("Failed to save transaction",isCreated),HttpStatus.BAD_REQUEST);
        }
    }

     @GetMapping("/{transactionId}")
    public ResponseEntity<Object> getTransactionById(@PathVariable Integer transactionId) {
         Object transaction = this.transactionService.getTransaction(transactionId);
         if (!ObjectUtils.isEmpty(transaction)){
             return new ResponseEntity<>(transaction, HttpStatus.OK);
            }
        else{
           return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/")

    public ResponseEntity<Object> getTransaction() {
        List<Transaction> transactionList = this.transactionService.getAllTransaction();
        if (!ObjectUtils.isEmpty(transactionList)){
            return new ResponseEntity<>(transactionList, HttpStatus.OK);
        }
        else{
            return new ResponseEntity<>(new ApiResponse("Fail to get all list",false),HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("update/")
    public ResponseEntity<Object> updateTransaction(@RequestBody Transaction transaction){
        Object savedTran = transactionService.updateTransaction(transaction);
        return new ResponseEntity(savedTran,HttpStatus.ACCEPTED);
    }

}
