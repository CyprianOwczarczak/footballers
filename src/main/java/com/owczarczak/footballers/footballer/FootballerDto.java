package com.owczarczak.footballers.footballer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FootballerDto {
    private Long id;
    private String pesel;
    private String name;
    private Integer height;
}
