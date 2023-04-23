package com.owczarczak.footballers.score;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScoreAddDto {

    private Long id;
    private Long matchId;
    private Long footballerId;
    private Integer minuteScored;
}
