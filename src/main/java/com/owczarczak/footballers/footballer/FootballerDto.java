package com.owczarczak.footballers.footballer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FootballerDto {
    private int id;
    private String pesel;
    private String name;
    private int height;
}
