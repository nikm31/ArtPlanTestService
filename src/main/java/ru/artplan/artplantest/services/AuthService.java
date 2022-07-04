package ru.artplan.artplantest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.artplan.artplantest.dtos.AuthRequest;
import ru.artplan.artplantest.dtos.AuthResponse;
import ru.artplan.artplantest.exceptions.DataValidationException;
import ru.artplan.artplantest.exceptions.ResourceNotFoundException;
import ru.artplan.artplantest.model.Attempt;
import ru.artplan.artplantest.model.User;
import ru.artplan.artplantest.utils.JwtTokenUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AuthService {

    private final UserService userService;

    private final AttemptService attemptService;

    private final JwtTokenUtil jwtTokenUtil;

    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(@Lazy UserService userService, AttemptService attemptService, JwtTokenUtil jwtTokenUtil, AuthenticationManager authenticationManager) {
        this.userService = userService;
        this.attemptService = attemptService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    public AuthResponse createAuthToken(AuthRequest authRequest) {
        User user = userService.findByUsername(authRequest.getUsername()).orElseThrow(
                () -> new ResourceNotFoundException("Юзер не найден"));

        Optional<Attempt> attempt = attemptService.findByUserId(user);
        boolean isAttemptExist = attempt.isPresent();

        if (isAttemptExist) {
            if (attempt.get().getCount() >= 10) {
                throw new DataValidationException(List.of("Исчерпано количество попыток авторизации за час. Авторизация отменена"));
            }
            if (LocalDateTime.now().isAfter(attempt.get().getTimeFinish())) {
                attemptService.delete(attempt.get());
            }
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException ex) {

            if (!isAttemptExist) {
                Attempt attemptUser = new Attempt();
                attemptUser.setUser(user);
                attemptUser.setCount(1L);
                attemptUser.setTimeFinish(LocalDateTime.now().plusHours(1));
                attemptService.save(attemptUser);
            }

            attempt.get().setCount(attempt.get().getCount() + 1L);
            attemptService.save(attempt.get());

            throw new DataValidationException(List.of("Не верное имя пользователя или пароль"));
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);

        if (isAttemptExist) {
            attemptService.delete(attempt.get());
        }

        return new AuthResponse(token);
    }
}
