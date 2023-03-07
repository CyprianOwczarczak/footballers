package com.owczarczak.footballers.clubRepresentation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClubRepresentationDto {
    int id;
    String clubName;
    int footballerListSize;
}
