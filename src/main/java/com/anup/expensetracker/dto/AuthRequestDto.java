// In: /dto/AuthRequestDto.java
package com.anup.expensetracker.dto;

import lombok.Data;

@Data
public class AuthRequestDto {
    private String email;
    private String password;
}