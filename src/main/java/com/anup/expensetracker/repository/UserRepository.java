// In: /repository/UserRepository.java
package com.anup.expensetracker.repository;

import com.anup.expensetracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // We'll need this for Spring Security
    Optional<User> findByEmail(String email);
}