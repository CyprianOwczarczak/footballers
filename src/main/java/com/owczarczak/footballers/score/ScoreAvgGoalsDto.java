package com.owczarczak.footballers.score;

import lombok.Builder;
import lombok.Data;

import java.math.BigInteger;

@Data
@Builder
public class ScoreAvgGoalsDto {
    Integer id;
    String footballerName;
    BigInteger numberOfGoals;
    BigInteger numberOfMatches;
    Double averageGoals;

    public ScoreAvgGoalsDto(Integer id, String footballerName, BigInteger numberOfGoals, BigInteger numberOfMatches, Double averageGoals) {
        this.id = id;
        this.footballerName = footballerName;
        this.numberOfGoals = numberOfGoals;
        this.numberOfMatches = numberOfMatches;
        this.averageGoals = averageGoals;
    }
}
