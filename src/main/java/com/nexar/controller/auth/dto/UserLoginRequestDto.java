package com.nexar.controller.auth.dto;

import lombok.Data;

@Data
public class UserLoginRequestDto {
    private String email;
    private String password;
}
