package com.owczarczak.footballers.score;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScoreDto {
    private int id;
    private int matchId;
    private String footballerName;
    private int minuteScored;
}
