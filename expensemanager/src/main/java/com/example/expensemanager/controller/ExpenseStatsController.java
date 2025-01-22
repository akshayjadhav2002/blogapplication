package com.example.expensemanager.controller;


import com.example.expensemanager.dto.ApiResponse;
import com.example.expensemanager.entity.ExpenseStatistic;
import com.example.expensemanager.entity.User;
import com.example.expensemanager.repository.UserRepository;
import com.example.expensemanager.services.ExpenseStatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/statistics/")
public class ExpenseStatsController {

    private ExpenseStatsService expenseStatsService;
    private UserRepository userRepository;
    ExpenseStatsController(ExpenseStatsService expenseStatsService,UserRepository userRepository){
        this.expenseStatsService = expenseStatsService;
        this.userRepository = userRepository;
    }

    @GetMapping("/category-wise")
    public ResponseEntity<Object> getCategoryWiseExpenses(Principal principal) {
        try {
            User user = userRepository.findFirstByUserName(principal.getName()).orElseThrow(
                    ()-> new RuntimeException(
                            "User not found " + principal.getName()
                    )
            );
            List<ExpenseStatistic> list = expenseStatsService.getExpenseStatsByUserId(user.getUserId());
            if(!list.isEmpty()) {
                log.info("list fetched successfully ");
                return new ResponseEntity<>(list, HttpStatus.OK);
            }
            else{
                log.info("Contains Empty List");
                return new ResponseEntity<>(new ApiResponse("Does not Contain any Expense",false),HttpStatus.NOT_FOUND);
            }
        }
        catch (Exception exception){
            log.warn("Exception Occurred while getting Statics");
            return new ResponseEntity<>(new ApiResponse("Fail to get Expense List",false),HttpStatus.BAD_REQUEST);
        }
    }
}
