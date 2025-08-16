// In: /repository/ExpenseRepository.java
package com.anup.expensetracker.repository;

import com.anup.expensetracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List; // Add this import

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByUserId(Long userId); // Add this method
}