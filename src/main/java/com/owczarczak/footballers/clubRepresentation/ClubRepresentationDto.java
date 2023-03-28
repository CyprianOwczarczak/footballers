package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.footballer.Footballer;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClubRepresentationDto {
    int id;
    int clubId;
    String clubName;
    List<Footballer> footballerList;
}
