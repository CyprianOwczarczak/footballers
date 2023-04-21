package com.owczarczak.footballers.score;

import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.footballer.FootballerRepository;
import com.owczarczak.footballers.match.Match;
import com.owczarczak.footballers.match.MatchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.hibernate.criterion.Projections.id;

@Service
@RequiredArgsConstructor
public class ScoreService {
    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private MatchRepository matchRepository;
    @Autowired
    private FootballerRepository footballerRepository;

    List<ScoreDto> getAllScores() {
        List<Score> scoreList = scoreRepository.findAll();
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

    Optional<ScoreDto> getScoreById(@PathVariable Long id) {
        if (!scoreRepository.existsById(id)) {
            return Optional.empty();
        } else {
            Score score = scoreRepository.getReferenceById(id);
            return Optional.ofNullable(ScoreDto.builder()
                    .id(score.getId())
                    .matchId(score.getMatch().getId())
                    .footballerName(score.getFootballer().getName())
                    .minuteScored(score.getMinuteScored())
                    .build());
        }
    }

    List<ScoreAvgGoalsDto> getAvgGoalsPerMatchForFootballer() {
        List<Object[]> returnedListOfArrays = scoreRepository.getAvgGoalsPerMatchForFootballer();
        List<ScoreAvgGoalsDto> dtos = new LinkedList<>();
        for (Object[] row : returnedListOfArrays) {
            ScoreAvgGoalsDto dtoToBeAdded = ScoreAvgGoalsDto.builder()
                    .id(((BigInteger) row[0]).longValue())
                    .footballerName((String) row[1])
                    .numberOfGoals(((BigInteger) row[2]).intValue())
                    .numberOfMatches(((BigInteger) row[3]).intValue())
                    .averageGoals((Double) row[4])
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    public ScoreAddDto addScore(ScoreAddDto scoreToBeAdded) {
        Match matchOptional = matchRepository.findById(scoreToBeAdded.getMatchId()).orElseThrow(RuntimeException::new);
        Footballer footballerOptional = footballerRepository
                .findById(scoreToBeAdded.getFootballerId()).orElseThrow(RuntimeException::new);

        Score newScore = Score.builder()
                .match(matchOptional)
                .footballer(footballerOptional)
                .minuteScored(scoreToBeAdded.getMinuteScored())
                .build();
        Score savedEntity = scoreRepository.save(newScore);

        return ScoreAddDto.builder()
                .id(savedEntity.getId())
                .matchId(savedEntity.getMatch().getId())
                .footballerId(savedEntity.getFootballer().getId())
                .minuteScored(savedEntity.getMinuteScored())
                .build();
    }

    @Transactional
    public void deleteMatchById(@PathVariable Long id) {
        if (scoreRepository.existsById(id)) {
            scoreRepository.deleteById(id);
        }
    }
}
