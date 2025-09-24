package com.example.expensemanager.services;

import com.example.expensemanager.entity.ExpenseStatistic;

import java.util.List;

public interface EmailService {
    void sendEmail(String toEmail, String subject, String htmlContent);
    String buildExpenseHtml(List<ExpenseStatistic> results);
}
