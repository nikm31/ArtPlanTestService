package ru.artplan.artplantest.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.artplan.artplantest.exceptions.ResourceNotFoundException;
import ru.artplan.artplantest.model.Animal;
import ru.artplan.artplantest.model.User;

import java.time.LocalDate;

@DataJpaTest
public class AnimalRepositoryTest {
    @Autowired
    private AnimalRepository animalRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void genreRepositoryTest() {
        User user = new User();
        user.setId(1L);
        user.setUsername("nikolay");
        user.setPassword("12345");
        user.setEmail("firewather@gmail.com");

        Animal animal = new Animal();
        animal.setName("kyzia");
        animal.setBirthDate(LocalDate.of(2022, 07,01));
        animal.setSex("male");
        animal.setOwner(user);

        entityManager.persist(animal);
        entityManager.flush();

        Animal aminalRep = animalRepository.findByName(animal.getName()).orElseThrow(()-> new ResourceNotFoundException("нет животного"));

        Assertions.assertEquals(aminalRep.getBirthDate(), animal.getBirthDate());
        Assertions.assertEquals(aminalRep.getSex(), "male");
    }
}
