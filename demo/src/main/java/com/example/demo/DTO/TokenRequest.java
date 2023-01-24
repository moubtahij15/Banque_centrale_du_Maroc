package com.example.demo.DTO;

import lombok.Data;

@Data
public class TokenRequest {
    private String grantType;
    private String username;
    private String password;
    private boolean withRefreshToken;
    private String role;
    private String refreshToken;
}
