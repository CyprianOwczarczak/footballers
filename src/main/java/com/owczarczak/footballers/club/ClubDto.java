package com.owczarczak.footballers.club;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.jackson.JsonObjectSerializer;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
public class ClubDto {
    private int id;
    private String name;
    //TODO zamiast Instant dać LocalDAte albo LocalDateTime i potem przerobić z JsonSerializer
    private LocalDate created;
}
