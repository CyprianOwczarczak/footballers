package com.owczarczak.footballers.footballer;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class FootballerDto {
    int id;
    private String pesel;
    private String name;
//    private String club;
    private int goals;
    private int height;
}
