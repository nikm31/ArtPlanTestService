package ru.artplan.artplantest.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.artplan.artplantest.model.Animal;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Schema(description = "Модель животного")
public class AnimalDto {

    @Schema(description = "ID животного", example = "1")
    private Long id;

    @Schema(description = "Название животного", required = true, maxLength = 255, minLength = 3, example = "Barsik")
    private String name;

    @Schema(description = "Дата рождения", required = true, example = "2022-07-01")
    private LocalDate birthDate;

    @Schema(description = "Пол", required = true, example = "male, female")
    private String sex;

    @Schema(description = "Хозяин", required = true, example = "Иван")
    private String owner;

    @JsonIgnore
    private LocalDateTime createdAt;

    @JsonIgnore
    private LocalDateTime updatedAt;

    public AnimalDto(Animal animal) {
        this.id = animal.getId();
        this.name = animal.getName();
        this.birthDate = animal.getBirthDate();
        this.sex = animal.getSex();
        this.owner = animal.getOwner().getUsername();
    }
}
