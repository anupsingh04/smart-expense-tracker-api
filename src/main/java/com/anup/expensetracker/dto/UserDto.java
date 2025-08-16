// In: /dto/UserDto.java
package com.anup.expensetracker.dto;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String email;
}