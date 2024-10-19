package com.example.expensemanager.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Transaction {
    @Id
    private Integer transactionId;
    private String name;
    private String description;
    private BigDecimal amount;
    private LocalDateTime dateTime;
    private String category;
    private String isDeleted;
}
