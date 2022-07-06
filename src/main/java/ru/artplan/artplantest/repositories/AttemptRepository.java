package ru.artplan.artplantest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.artplan.artplantest.model.Attempt;
import ru.artplan.artplantest.model.User;

import java.util.Optional;

@Repository
public interface AttemptRepository extends JpaRepository<Attempt, Long> {

    Optional<Attempt> findByUser(User user);

}
