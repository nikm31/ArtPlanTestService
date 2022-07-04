package ru.artplan.artplantest.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.artplan.artplantest.model.Role;
import ru.artplan.artplantest.repositories.RoleRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Optional<Role> findByName(String name) {
        return roleRepository.findByName(name);
    }
}
