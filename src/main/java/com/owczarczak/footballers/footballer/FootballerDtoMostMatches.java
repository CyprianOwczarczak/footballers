package com.owczarczak.footballers.footballer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FootballerDtoMostMatches {
    private Long id;
    private Integer representationSize;
}
