package ru.artplan.artplantest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.artplan.artplantest.dtos.AnimalDto;
import ru.artplan.artplantest.exceptions.DataValidationException;
import ru.artplan.artplantest.exceptions.ResourceNotFoundException;
import ru.artplan.artplantest.services.AnimalService;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/animal")
@RequiredArgsConstructor
@Tag(name = "Животные", description = "Методы работы с животными")
public class AnimalController {
    private final AnimalService animalService;

    @Operation(
            summary = "Запрос на получение животного по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = AnimalDto.class))
                    ),
                    @ApiResponse(
                            description = "Животное не найдено", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
                    )
            }
    )
    @GetMapping(path = "/{id}")
    public AnimalDto findAnimalById(@PathVariable Long id) {
        return new AnimalDto(animalService.findAnimalById(id).orElseThrow(
                () -> new ResourceNotFoundException("Животное с таким id не найдено: " + id)));
    }

    @Operation(
            summary = "Запрос на удаление животного по id",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200"
                    ),
                    @ApiResponse(
                            description = "Животное не найдено", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
                    )
            }
    )
    @DeleteMapping(path = "/{id}")
    public void deleteAnimal(@PathVariable Long id) {
        animalService.deleteAnimal(id);
    }

    @Operation(
            summary = "Запрос на получение списка животных с пагинацией",
            responses = {
                    @ApiResponse(
                            description = "Успешный ответ", responseCode = "200",
                            content = @Content(schema = @Schema(implementation = Page.class))
                    )
            }
    )
    @GetMapping
    public Page<AnimalDto> getAllUserAnimals(
            @RequestParam(value = "page") Optional<Integer> page,
            @RequestParam(value = "pageSize") Optional<Integer> pageSize,
            Principal principal
    ) {
        final int currentPageSize = pageSize.orElse(0) < 5 ? 5 : pageSize.get();
        final int currentPage = page.orElse(0) < 1 ? 0 : page.get() - 1;
        return animalService.getAllUserAnimals(currentPage, currentPageSize, principal).map(AnimalDto::new);
    }

    @Operation(
            summary = "Запрос на создание нового животного",
            responses = {
                    @ApiResponse(
                            description = "Животное успешно создано", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = AnimalDto.class))
                    ),
                    @ApiResponse(
                            description = "Животное с таким именем уже существует", responseCode = "400",
                            content = @Content(schema = @Schema(implementation = DataValidationException.class))
                    ),
                    @ApiResponse(
                            description = "Пользователь не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
                    )
            }
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AnimalDto createNewAnimal(@RequestBody AnimalDto animalDto) {
        return new AnimalDto(animalService.createNewAnimal(animalDto));
    }

    @Operation(
            summary = "Запрос на изменение животного",
            responses = {
                    @ApiResponse(
                            description = "Животное успешно изменено", responseCode = "201",
                            content = @Content(schema = @Schema(implementation = AnimalDto.class))
                    ),
                    @ApiResponse(
                            description = "Животное или пользователь не найден", responseCode = "404",
                            content = @Content(schema = @Schema(implementation = ResourceNotFoundException.class))
                    )
            }
    )
    @PutMapping
    public AnimalDto changeAnimal(@RequestBody AnimalDto animalDto) {
        return new AnimalDto(animalService.changeAnimal(animalDto));
    }


}
