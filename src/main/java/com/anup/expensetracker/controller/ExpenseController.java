// In: /controller/ExpenseController.java
package com.anup.expensetracker.controller;

import com.anup.expensetracker.dto.ExpenseDto;
import com.anup.expensetracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
// Add these imports
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin; // Add this import
import org.springframework.web.bind.annotation.RequestParam; // Add this import

@RestController
@RequestMapping("/api/expenses")
@CrossOrigin(origins = "http://localhost:5173") // Allow requests from our React app

public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    // ... Autowire ObjectMapper
    @Autowired
    private ObjectMapper objectMapper;

    // NEW - PUT endpoint to update an existing expense
    @PutMapping("/{id}")
    public ResponseEntity<ExpenseDto> updateExpense(@PathVariable Long id, @RequestBody ExpenseDto expenseDto) {
        ExpenseDto updatedExpense = expenseService.updateExpense(id, expenseDto);
        return ResponseEntity.ok(updatedExpense);
    }

    // NEW - DELETE endpoint to delete an expense
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build(); // Standard for successful DELETE
    }

    // Replace the old createExpense method
    @PostMapping
    public ResponseEntity<ExpenseDto> createExpense(
            @RequestParam("expense") String expenseDtoStr,
            @RequestParam(value = "file", required = false) MultipartFile file,
            Principal principal) throws IOException {

        // Deserialize the JSON string to an ExpenseDto object
        ExpenseDto expenseDto = objectMapper.readValue(expenseDtoStr, ExpenseDto.class);

        String userEmail = principal.getName();
        ExpenseDto createdExpense = expenseService.createExpense(expenseDto, userEmail, file);
        return new ResponseEntity<>(createdExpense, HttpStatus.CREATED);
    }

    // GET endpoint to retrieve a single expense by its ID
    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDto> getExpenseById(@PathVariable Long id) {
        ExpenseDto expenseDto = expenseService.getExpenseById(id);
        return ResponseEntity.ok(expenseDto);
    }

    // Replace the old getAllExpenses method with this one
    @GetMapping
    public ResponseEntity<List<ExpenseDto>> getAllExpenses(@RequestParam(required = false) Long userId) {
        List<ExpenseDto> expenses;
        if (userId != null) {
            expenses = expenseService.getExpensesByUserId(userId); // We will create this service method next
        } else {
            expenses = expenseService.getAllExpenses();
        }
        return ResponseEntity.ok(expenses);
    }
}