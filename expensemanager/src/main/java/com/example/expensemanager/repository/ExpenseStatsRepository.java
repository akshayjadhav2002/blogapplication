package com.example.expensemanager.repository;

import com.example.expensemanager.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseStatsRepository extends JpaRepository<Transaction, Integer> {

    @Query(
            value = "SELECT c.category, SUM(t.amount) AS amount " +
                    "FROM transaction t " +
                    "LEFT JOIN category c ON t.category_id = c.category_id " +
                    "WHERE t.user_id = :userId " +
                    "GROUP BY c.category_id",
            nativeQuery = true
    )
    List<Object[]> getAllExpenseCategoryWise(Integer userId);


    @Query(
            value = """
                    SELECT\s
                        c.category,\s
                        SUM(t.amount) AS amount
                    FROM transaction t
                    LEFT JOIN category c\s
                        ON t.category_id = c.category_id
                    WHERE\s
                        t.user_id = :userId
                        AND t.date_time >= DATE_SUB(NOW(), INTERVAL 1 MONTH)
                    GROUP BY\s
                        c.category_id;
                    
                    """,
            nativeQuery = true
    )
    List<Object[]> getAllExpenseCategoryWiseForMonth(Integer userId);
}
