package com.linkedIn.userService.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {
    private String email, password;
}
