package com.owczarczak.footballers.score;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScoreAddDto {

    private int id;
    private int matchId;
    private int footballerId;
    private int minuteScored;
}
