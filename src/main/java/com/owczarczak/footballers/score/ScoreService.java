package com.owczarczak.footballers.score;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreService {
    @Autowired
    ScoreRepository repository;

    List<ScoreAvgGoalsDto> getAvgGoalsPerMatchForFootballer() {
        List<Object[]> returnedListOfArrays = repository.getAvgGoalsPerMatchForFootballer();
        List<ScoreAvgGoalsDto> dtos = new LinkedList<>();
        for (Object[] row : returnedListOfArrays) {
            ScoreAvgGoalsDto dtoToBeAdded = ScoreAvgGoalsDto.builder()
                    .id((Integer) row[0])
                    .footballerName((String) row[1])
                    .numberOfGoals((BigInteger) row[2])
                    .numberOfMatches((BigInteger) row[3])
                    .averageGoals((Double) row[4])
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }
}
