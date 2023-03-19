package com.owczarczak.footballers.score;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreService {
    @Autowired
    ScoreRepository repository;

    List<ScoreDto> getAllScores() {
        List<Score> scoreList = repository.findAll();
        List<ScoreDto> dtos = new LinkedList<>();
        for (Score score : scoreList) {
            ScoreDto dtoToBeAdded = ScoreDto.builder()
                    .id(score.getId())
                    .matchId(score.getMatch().getId())
                    .footballerName(score.getFootballer().getName())
                    .minuteScored(score.getMinuteScored())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    Optional<ScoreDto> getScoreById(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        } else {
            Score score = repository.getReferenceById(id);
            return Optional.ofNullable(ScoreDto.builder()
                    .id(score.getId())
                    .matchId(score.getMatch().getId())
                    .footballerName(score.getFootballer().getName())
                    .minuteScored(score.getMinuteScored())
                    .build());
        }
    }

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
