package com.owczarczak.footballers.score;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScoreDto {
    private Long id;
    private Long matchId;
    private String footballerName;
    private Integer minuteScored;
}
