package com.owczarczak.footballers.score;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ScoreAvgGoalsDto {
    private Long id;
    private String footballerName;
    private Integer numberOfGoals;
    private Integer numberOfMatches;
    private Double averageGoals;

    public ScoreAvgGoalsDto(Long id, String footballerName, Integer numberOfGoals, Integer numberOfMatches, Double averageGoals) {
        this.id = id;
        this.footballerName = footballerName;
        this.numberOfGoals = numberOfGoals;
        this.numberOfMatches = numberOfMatches;
        this.averageGoals = averageGoals;
    }
}
