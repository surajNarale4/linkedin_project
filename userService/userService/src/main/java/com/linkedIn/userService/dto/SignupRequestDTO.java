package com.linkedIn.userService.dto;

import lombok.Data;

@Data
public class SignupRequestDTO {
    private String name, email, password;
}
