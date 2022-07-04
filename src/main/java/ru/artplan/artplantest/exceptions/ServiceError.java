package ru.artplan.artplantest.exceptions;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class ServiceError {
    private List<String> messages;
    private Date date;

    public ServiceError(List<String> messages) {
        this.messages = messages;
        this.date = new Date();
    }

    public ServiceError(String message) {
        this(List.of(message));
    }

    public ServiceError(String... messages) {
        this(Arrays.asList(messages));
    }
}
