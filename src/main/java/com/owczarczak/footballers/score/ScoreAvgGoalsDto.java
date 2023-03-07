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
}
