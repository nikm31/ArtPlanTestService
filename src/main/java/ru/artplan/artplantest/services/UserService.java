package ru.artplan.artplantest.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.artplan.artplantest.dtos.AuthRequest;
import ru.artplan.artplantest.dtos.AuthResponse;
import ru.artplan.artplantest.exceptions.DataValidationException;
import ru.artplan.artplantest.exceptions.ResourceNotFoundException;
import ru.artplan.artplantest.model.Role;
import ru.artplan.artplantest.model.User;
import ru.artplan.artplantest.repositories.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;

    private final RoleService roleService;

    @Lazy
    private final AuthService authService;

    @Value("${roles_def.default}")
    private String defaultRole;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Transactional
    public AuthResponse registerNewUser(AuthRequest userDto) {

        if (findByUsername(userDto.getUsername()).isPresent()) {
            throw new DataValidationException(List.of("Пользователь уже существует: "+ userDto.getUsername()));
        }

        Role role = roleService.findByName(defaultRole).orElseThrow(
                ()-> new ResourceNotFoundException("Дефолтная роль юзера не найдена"));

        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setRoles(List.of(role));
        userRepository.save(user);

        return authService.createAuthToken(userDto);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}