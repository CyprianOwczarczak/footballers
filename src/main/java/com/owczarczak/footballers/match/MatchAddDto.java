package com.owczarczak.footballers.match;

import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.score.Score;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
public class MatchAddDto {
    private int id;
    private int hostId;
    private int guestId;
    private String nameOfReferee;
    private String date;
//    private
    //TODO
    //Add ScoreDto list
    //json-> String javowy
    //custom mapper w jacksonie
}
