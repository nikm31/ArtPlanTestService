package ru.artplan.artplantest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.artplan.artplantest.dtos.AnimalDto;
import ru.artplan.artplantest.dtos.AuthRequest;
import ru.artplan.artplantest.dtos.AuthResponse;
import ru.artplan.artplantest.exceptions.DataValidationException;
import ru.artplan.artplantest.exceptions.ResourceNotFoundException;
import ru.artplan.artplantest.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Методы работы с пользователями")
public class UserController {
    private final UserService userService;

    @Operation(
            summary = "Запрос на создание нового пользователя",
            responses = {
                    @ApiResponse(
                            description = "Пользователь создан", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = AnimalDto.class))
                    ),
                    @ApiResponse(
                            description = "Пользователь уже существует", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = DataValidationException.class))
                    ),
                    @ApiResponse(
                            description = "Дефолтная роль юзера не найдена", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
                    )
            }
    )
    @PostMapping("register")
    public AuthResponse registerUser(@RequestBody AuthRequest user) {
        return userService.registerNewUser(user);
    }

    @Operation(
            summary = "Запрос на проверку доступности имени пользователя",
            responses = {
                    @ApiResponse(
                            description = "Логин свободен", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = AnimalDto.class))
                    ),
                    @ApiResponse(
                            description = "Пользователь существует", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = DataValidationException.class))
                    ),
            }
    )
    @PostMapping("check_login")
    public ResponseEntity<?> checkLogin(@RequestBody AuthRequest user) {
        if (userService.findByUsername(user.getUsername()).isPresent()) {
            throw new DataValidationException(List.of("Пользователь существует: " + user.getUsername()));
        }
        return new ResponseEntity<>("Логин свободен: " + user.getUsername(), HttpStatus.OK);
    }

}
