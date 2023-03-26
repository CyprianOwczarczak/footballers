package com.owczarczak.footballers.score;

import com.owczarczak.footballers.footballer.FootballerDto;
import com.owczarczak.footballers.match.MatchDto;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ScoreNewDto {
    private int id;

    private int matchId;
    private Instant matchDate;

    private int footballerId;
    private String footballerPesel;
    private String footballerName;

    private int minuteScored;

    public ScoreNewDto(Integer id, Integer matchId, Instant matchDate, Integer footballerId, String footballerPesel, String footballerName, Integer minuteScored) {
        this.id = id;
        this.matchId = matchId;
        this.matchDate = matchDate;
        this.footballerId = footballerId;
        this.footballerPesel = footballerPesel;
        this.footballerName = footballerName;
        this.minuteScored = minuteScored;
    }
}
