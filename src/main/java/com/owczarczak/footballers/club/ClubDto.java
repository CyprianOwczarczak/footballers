package com.owczarczak.footballers.club;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    private LocalDate created;
}
