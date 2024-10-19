package com.example.expensemanager.controller;

import com.example.expensemanager.services.TransactionService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController{

    private TransactionService transactionService;

    public TransactionController ( TransactionService transactionService){
        this.transactionService = transactionService;
    }

     @GetMapping("/")
    public ResponseEntity<Object> getTransaction(@PathVariable Integer transactionId) {
         Object tranaction = this.transactionService.getTransaction(transactionId);
         if (!ObjectUtils.isEmpty(tranaction)){
             return new ResponseEntity<>(tranaction, HttpStatus.OK);
            }
        else{
           return new ResponseEntity<>(false,HttpStatus.BAD_REQUEST);
        }
    }
}
