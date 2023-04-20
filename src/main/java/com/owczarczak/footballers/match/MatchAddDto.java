package com.owczarczak.footballers.match;

import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationAddDto;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationDto;
import com.owczarczak.footballers.score.ScoreAddDto;
import com.owczarczak.footballers.score.ScoreDto;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class MatchAddDto {
    private int id;
    private ClubRepresentationAddDto guestRepresentation;
    private ClubRepresentationAddDto hostRepresentation;
    private String nameOfReferee;
    private LocalDateTime date;
    private List<ScoreAddDto> scoresList;
}
