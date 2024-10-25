package com.example.expensemanager.services;


import com.example.expensemanager.entity.ExpenseStatistic;

import java.util.List;

public interface ExpenseStatsService {
   List<ExpenseStatistic> getExpenseStatsByUserId(Integer userId);
}
