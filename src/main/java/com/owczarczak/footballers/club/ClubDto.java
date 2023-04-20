package com.owczarczak.footballers.club;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClubDto {
    private int id;

    private String name;

    private LocalDate created;
}
