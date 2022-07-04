package ru.artplan.artplantest.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Schema(description = "токен авторизации")
public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }
}
