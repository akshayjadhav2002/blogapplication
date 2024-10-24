package com.example.expensemanager.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {

    private Integer transactionId;
    private String name;
    private String description;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private Integer categoryId;
    private Integer userId;
}
