package ru.artplan.artplantest.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.artplan.artplantest.model.User;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class UserDto {
    private String username;

    private String password;

    private String email;

    public UserDto(User user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
    }
}
