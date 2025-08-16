// In: /service/ExpenseService.java
package com.anup.expensetracker.service;
import com.anup.expensetracker.entity.Role;

import com.anup.expensetracker.dto.ExpenseDto;
import com.anup.expensetracker.entity.Category;
import com.anup.expensetracker.entity.Expense;
import com.anup.expensetracker.entity.User;
import com.anup.expensetracker.repository.CategoryRepository;
import com.anup.expensetracker.repository.ExpenseRepository;
import com.anup.expensetracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    // Autowire the new service
    @Autowired
    private FileStorageService fileStorageService;

    // Replace the old createExpense method with this one
    public ExpenseDto createExpense(ExpenseDto expenseDto, String userEmail, MultipartFile file) {
        // Find user by email instead of ID from the DTO
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + userEmail));

        Category category = categoryRepository.findById(expenseDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        String fileName = null; // Declare fileName in the outer scope
        if (file != null && !file.isEmpty()) {
            fileName = fileStorageService.storeFile(file); // Assign value inside the if-block
        }

        Expense expense = new Expense();
        expense.setTitle(expenseDto.getTitle());
        expense.setAmount(expenseDto.getAmount());
        expense.setDescription(expenseDto.getDescription());
        expense.setDate(expenseDto.getDate());
        expense.setUser(user);
        expense.setCategory(category);
        expense.setReceiptUrl(fileName); // Save the unique file name

        Expense savedExpense = expenseRepository.save(expense);
        return mapToDto(savedExpense);
    }

    // Get a single expense by ID
    public ExpenseDto getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found"));
        return mapToDto(expense);
    }

    // Add this new method
    public List<ExpenseDto> getExpensesByUserId(Long userId) {
        return expenseRepository.findByUserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Get all expenses
    public List<ExpenseDto> getAllExpenses() {
        return expenseRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    // Helper method to map Entity to DTO
    private ExpenseDto mapToDto(Expense expense) {
        ExpenseDto dto = new ExpenseDto();
        dto.setId(expense.getId());
        dto.setTitle(expense.getTitle());
        dto.setAmount(expense.getAmount());
        dto.setDescription(expense.getDescription());
        dto.setDate(expense.getDate());
        dto.setUserId(expense.getUser().getId());
        dto.setUserName(expense.getUser().getName()); // Add this line
        dto.setCategoryId(expense.getCategory().getId());
        dto.setReceiptUrl(expense.getReceiptUrl()); // Add this line
        return dto;
    }

    // NEW - Update an existing expense
    public ExpenseDto updateExpense(Long id, ExpenseDto expenseDto) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Expense not found with id: " + id));

        User user = userRepository.findById(expenseDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Category category = categoryRepository.findById(expenseDto.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingExpense.setTitle(expenseDto.getTitle());
        existingExpense.setAmount(expenseDto.getAmount());
        existingExpense.setDescription(expenseDto.getDescription());
        existingExpense.setDate(expenseDto.getDate());
        existingExpense.setUser(user);
        existingExpense.setCategory(category);

        Expense updatedExpense = expenseRepository.save(existingExpense);
        return mapToDto(updatedExpense);
    }

    // NEW - Delete an expense
    public void deleteExpense(Long id) {
        if (!expenseRepository.existsById(id)) {
            throw new RuntimeException("Expense not found with id: " + id);
        }
        expenseRepository.deleteById(id);
    }

}