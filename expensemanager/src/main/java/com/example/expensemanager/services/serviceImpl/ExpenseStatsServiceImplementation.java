package com.example.expensemanager.services.serviceImpl;

import com.example.expensemanager.entity.ExpenseStatistic;
import com.example.expensemanager.repository.ExpenseStatsRepository;
import com.example.expensemanager.services.ExpenseStatsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ExpenseStatsServiceImplementation implements ExpenseStatsService {


    private ExpenseStatsRepository expenseStatsRepository;

    ExpenseStatsServiceImplementation (ExpenseStatsRepository expenseStatsRepository){
        this.expenseStatsRepository = expenseStatsRepository;
    }

    public List<ExpenseStatistic> getExpenseStatsByUserId(Integer userId) {
            List<Object[]> results = expenseStatsRepository.getAllExpenseCategoryWise(userId);
            List<ExpenseStatistic> expenseStats = new ArrayList<>();
//            System.err.println(results.size());
            for (Object[] row : results) {
                String category = (String) row[0];
                BigDecimal amount = (BigDecimal) row[1];
                expenseStats.add(new ExpenseStatistic(category, amount));
            }
            return expenseStats;
    }

    public List<ExpenseStatistic> getExpenseStatsByUserIdForMonth(Integer userId) {
        List<Object[]> results = expenseStatsRepository.getAllExpenseCategoryWiseForMonth(userId);
        List<ExpenseStatistic> expenseStatsForMonth = new ArrayList<>();
//            System.err.println(results.size());
        for (Object[] row : results) {
            String category = (String) row[0];
            BigDecimal amount = (BigDecimal) row[1];
            expenseStatsForMonth.add(new ExpenseStatistic(category, amount));
        }
        return expenseStatsForMonth;
    }

}
