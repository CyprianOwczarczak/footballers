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

    //TODO Zserializować całe DTO, LocalDate na wyjściu ma być typu String i z powrotem ma się deserializować jako LocalDate

    //Mam otrzymać takiego JSON'a i jeśli prześlę takiego JSON'a to ma go zaakceptować
//    {
//        "id":1,
//            "name":"Piotr",
//            "created":"2022-07-10"
//    }
}
