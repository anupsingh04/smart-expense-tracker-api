// In: /repository/CategoryRepository.java
package com.anup.expensetracker.repository;

import com.anup.expensetracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}