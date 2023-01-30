package com.owczarczak.footballers.footballer;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class FootballerDto {
    @NotBlank
    private String pesel;
    @NotBlank
    private String name;
    @NotBlank
    private String club;
    @NotNull
    private int goals;
    @NotNull
    @Min(value = 1, message = "Height of the footballer must be higher than zero !")
    private int height;

}
