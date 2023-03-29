package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.footballer.Footballer;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClubRepresentationDto {
    private int id;
    private int clubId;
    private String clubName;
    private List<Footballer> footballerList;
}
