package com.example.expensemanager.controller;

import com.example.expensemanager.dto.ApiResponse;
import com.example.expensemanager.dto.TransactionDTO;
import com.example.expensemanager.entity.Transaction;
import com.example.expensemanager.entity.User;
import com.example.expensemanager.repository.UserRepository;
import com.example.expensemanager.services.TransactionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/transactions")
public class TransactionController{

    private TransactionService transactionService;
    private  UserRepository userRepository;
    //logging slf4j object
    static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    public TransactionController (TransactionService transactionService, UserRepository userRepository){
        this.transactionService = transactionService;
        this.userRepository = userRepository;
    }

    @PostMapping("/createExpense/")
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionDTO transactionDto, Principal principal){
        try {
        transactionDto.setDateTime(LocalDateTime.now());
        User user = userRepository.findFirstByUserName(principal.getName()).orElseThrow(()->
                new RuntimeException("user not found"));
        transactionDto.setUserId(user.getUserId());
        Boolean isCreated =  this.transactionService.createTransaction(transactionDto);
            logger.info("transaction created successfully");
            return new ResponseEntity(new ApiResponse("Transaction saved successfully", isCreated), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.info("transaction failed to create");
            return new ResponseEntity<>(new ApiResponse("Failed to save transaction",false),HttpStatus.BAD_REQUEST);
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
    ///Get transaction by the userId depending on which user is login in
    @GetMapping("/list/")
    public ResponseEntity<Object> getAllTransaction(Principal principal) {
        try {
        User user = userRepository.findFirstByUserName(principal.getName()).orElseThrow(
                () -> new RuntimeException("User Not Found with User name " + principal.getName())
        );
        List<Transaction> transactionList = this.transactionService.getAllTransaction(user.getUserId());

            logger.info("transaction list fetched  successfully");
            return new ResponseEntity<>(transactionList, HttpStatus.OK);
        }
        catch (Exception exception){
            exception.printStackTrace();
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
