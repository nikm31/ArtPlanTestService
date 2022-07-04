package ru.artplan.artplantest.repositories;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.artplan.artplantest.model.Animal;
import ru.artplan.artplantest.model.User;

import java.util.Optional;

@Repository
public interface AnimalRepository extends PagingAndSortingRepository<Animal, Long>, JpaRepository<Animal, Long> {

    Optional<Animal> findById(Long id);

    Page<Animal> findAllByOwner(User owner, Pageable pageable);

    Optional<Animal> findByName(String name);
}
