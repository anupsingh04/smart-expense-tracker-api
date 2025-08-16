// In: /dto/RegisterRequestDto.java
package com.anup.expensetracker.dto;

import lombok.Data;

@Data
public class RegisterRequestDto {
    private String name;
    private String email;
    private String password;
}