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
    private ClubRepresentation host;
    private ClubRepresentation guest;
    private String nameOfReferee;
    private Instant date;
    private List<Score> scores;
}
