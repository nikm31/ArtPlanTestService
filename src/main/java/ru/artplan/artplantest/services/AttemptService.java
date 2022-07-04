package ru.artplan.artplantest.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.artplan.artplantest.model.Attempt;
import ru.artplan.artplantest.model.User;
import ru.artplan.artplantest.repositories.AttemptRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttemptService {
    private final AttemptRepository attemptRepository;

    public Optional<Attempt> findByUserId(User user) {
        return attemptRepository.findByUser(user);
    }
    public void save(Attempt attempt) {
        attemptRepository.save(attempt);
    }
    public void delete(Attempt attempt) {
        attemptRepository.delete(attempt);
    }

}
