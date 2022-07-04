package ru.artplan.artplantest.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.artplan.artplantest.dtos.AnimalDto;
import ru.artplan.artplantest.exceptions.DataValidationException;
import ru.artplan.artplantest.exceptions.ResourceNotFoundException;
import ru.artplan.artplantest.model.Animal;
import ru.artplan.artplantest.model.User;
import ru.artplan.artplantest.repositories.AnimalRepository;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalService {
    private final AnimalRepository animalRepository;
    private final UserService userService;

    public Optional<Animal> findAnimalById(Long id) {
        return animalRepository.findById(id);
    }

    public Page<Animal> getAllUserAnimals(int pageNumber, int pageSize, Principal principal) {
        User user = userService.findByUsername(principal.getName()).orElseThrow(
                () -> new ResourceNotFoundException("Юзер не найден" + principal.getName()));
        return animalRepository.findAllByOwner(user, PageRequest.of(pageNumber, pageSize));
    }

    public Animal createNewAnimal(AnimalDto animalDto) {

        if (animalRepository.findByName(animalDto.getName()).isPresent()) {
            throw new DataValidationException(List.of("Животное с таким именем уже существует: " + animalDto.getName()));
        }

        User user = userService.findByUsername(animalDto.getOwner()).orElseThrow(
                () -> new ResourceNotFoundException("Не удалось записать животного, пользователь не найден: " + animalDto.getOwner()));

        Animal animal = new Animal();
        animal.setName(animalDto.getName());
        animal.setBirthDate(animalDto.getBirthDate());
        animal.setSex(animalDto.getSex());
        animal.setOwner(user);
        animalRepository.save(animal);
        return animal;
    }

    @Transactional
    public Animal changeAnimal(AnimalDto animalDto) {

        Animal animal = findAnimalById(animalDto.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Не могу изменить животное, id не найден: " + animalDto.getId()));

        User user = userService.findByUsername(animalDto.getOwner()).orElseThrow(
                () -> new ResourceNotFoundException("Не удалось изменить животного, пользователь не найден: " + animalDto.getOwner()));

        animal.setName(animalDto.getName());
        animal.setBirthDate(animalDto.getBirthDate());
        animal.setSex(animalDto.getSex());
        animal.setOwner(user);
        animal.setUpdatedAt(LocalDateTime.now());
        animalRepository.save(animal);
        return animal;
    }

    public void deleteAnimal(Long id) {
        Animal animal = animalRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Не найдено животное с таким Ид: " + id));
        animalRepository.delete(animal);
    }

}
