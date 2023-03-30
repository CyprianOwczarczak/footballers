package com.owczarczak.footballers.footballer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FootballerDtoMostMatches {
    private int id;
    private int representationSize;
}
