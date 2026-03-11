package com.nexar.controller.auth.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthTokenDto {
    private String idToken;
}
