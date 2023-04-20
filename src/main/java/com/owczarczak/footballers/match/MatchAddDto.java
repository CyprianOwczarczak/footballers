package com.owczarczak.footballers.match;

import com.owczarczak.footballers.clubRepresentation.ClubRepresentationAddDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class MatchAddDto {
    private int id;
    private ClubRepresentationAddDto guestRepresentation;
    private ClubRepresentationAddDto hostRepresentation;
    private String nameOfReferee;
    private LocalDateTime date;
}
