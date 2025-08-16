// In: /dto/AuthResponseDto.java
package com.anup.expensetracker.dto;

import lombok.Data;

@Data
public class AuthResponseDto {
    private String jwtToken;

    public AuthResponseDto(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}