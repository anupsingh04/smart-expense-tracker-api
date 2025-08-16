// In: /dto/ExpenseDto.java
package com.anup.expensetracker.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ExpenseDto {
    private Long id;
    private String title;
    private BigDecimal amount;
    private String description;
    private LocalDate date;
    private Long categoryId; // We'll use IDs to link entities
    private Long userId;
    private String userName; // Add this line
    private String receiptUrl; // Add this line
}