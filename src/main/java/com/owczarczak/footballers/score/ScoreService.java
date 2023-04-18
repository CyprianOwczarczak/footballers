package com.owczarczak.footballers.score;

import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.footballer.FootballerDto;
import com.owczarczak.footballers.match.Match;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScoreService {
    @Autowired
    private ScoreRepository repository;

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

//    List<ScoreAddDto> getAvgNew() {
//        List<ScoreAddDto> returnedList = repository.getAvgNew();
//        List<ScoreAddDto> dtos = new LinkedList<>();
//        for (ScoreAddDto score : returnedList) {
//            ScoreAddDto dtoToBeAdded = ScoreAddDto.builder()
//                    .id(score.getId())
//                    .matchId(score.getMatchId())
//                    .matchDate(score.getMatchDate())
//                    .footballerId(score.getFootballerId())
//                    .footballerPesel(score.getFootballerPesel())
//                    .footballerName(score.getFootballerName())
//                    .minuteScored(score.getMinuteScored())
//                    .build();
//            dtos.add(dtoToBeAdded);
//        }
//        return dtos;
//    }

    public ScoreAddDto addScore(ScoreAddDto scoreToBeAdded) {

        Score newScore = Score.builder()
                .match(repository.getReferenceById(scoreToBeAdded.getMatchId()).getMatch())
                .footballer(repository.getReferenceById(scoreToBeAdded.getFootballerId()).getFootballer())
                .minuteScored(scoreToBeAdded.getMinuteScored())
                .build();
        Score savedEntity = repository.save(newScore);

        return ScoreAddDto.builder()
                .id(savedEntity.getId())
                .matchId(savedEntity.getMatch().getId())
                .footballerId(savedEntity.getFootballer().getId())
                .minuteScored(savedEntity.getMinuteScored())
                .build();
    }

    @Transactional
    public void deleteMatchById(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}
