package ru.artplan.artplantest.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.artplan.artplantest.model.User;

@NoArgsConstructor
@Data
@Schema(description = "Запрос авторизации / регистрации")
public class AuthRequest {

    @Schema(description = "логин", example = "nikolay", required = true)
    private String username;

    @Schema(description = "пароль", example = "100", required = true)
    private String password;

    public AuthRequest(UserDto user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
    }
}
