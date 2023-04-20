package com.owczarczak.footballers.match;

import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationService;
import com.owczarczak.footballers.footballer.FootballerRepository;
import com.owczarczak.footballers.score.Score;
import com.owczarczak.footballers.score.ScoreAddDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    @Autowired
    MatchRepository matchRepository;
    @Autowired
    ClubRepresentationRepository representationRepository;
    @Autowired
    ClubRepresentationService representationService;

    @Autowired
    FootballerRepository footballerRepository;

    List<MatchDto> getAllMatches() {
        List<Match> matchList = matchRepository.findAll();
        List<MatchDto> dtos = new LinkedList<>();
        for (Match match : matchList) {
            MatchDto dtoToBeAdded = MatchDto.builder()
                    .id(match.getId())
                    .hostClubName(match.getHost().getClub().getName())
                    .guestClubName(match.getGuest().getClub().getName())
                    .nameOfReferee(match.getNameOfReferee())
                    .date(match.getDate())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    Optional<MatchDto> getMatchById(@PathVariable int id) {
        if (!matchRepository.existsById(id)) {
            return Optional.empty();
        } else {
            Match match = matchRepository.getReferenceById(id);
            return Optional.ofNullable(MatchDto.builder()
                    .id(match.getId())
                    .hostClubName(match.getHost().getClub().getName())
                    .guestClubName(match.getGuest().getClub().getName())
                    .nameOfReferee(match.getNameOfReferee())
                    .date(match.getDate())
                    .build());
        }
    }

    List<MatchDto> findByRefereeName(String nameOfReferee) {
        List<Match> matchList = matchRepository.findByRefereeName(nameOfReferee);
        List<MatchDto> dtos = new LinkedList<>();
        for (Match match : matchList) {
            MatchDto dtoToBeAdded = MatchDto.builder()
                    .id(match.getId())
                    .hostClubName(match.getHost().getClub().getName())
                    .guestClubName(match.getGuest().getClub().getName())
                    .nameOfReferee(match.getNameOfReferee())
                    .date(match.getDate())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    public MatchAddDto addMatch(MatchAddDto matchToBeAdded) {

        Match newMatch = Match.builder()
                .guest(representationService.toEntity(matchToBeAdded.getGuestRepresentation()))
                .host(representationService.toEntity(matchToBeAdded.getHostRepresentation()))
                .nameOfReferee(matchToBeAdded.getNameOfReferee())
                .date(matchToBeAdded.getDate())
                .scores(toScoreEntityList(matchToBeAdded.getScoresList()))
                .build();
        Match savedEntity = matchRepository.save(newMatch);

        return MatchAddDto.builder()
                .id(savedEntity.getId())
                .guestRepresentation(representationService.toRepresentationDto(savedEntity.getGuest()))
                .hostRepresentation(representationService.toRepresentationDto(savedEntity.getHost()))
                .nameOfReferee(savedEntity.getNameOfReferee())
                .date(savedEntity.getDate())
                .scoresList(listToDto(savedEntity.getScores()))
                .build();
    }



    List<Score> toScoreEntityList(List<ScoreAddDto> scoreDtos) {
        List<Score> convertedScores = new LinkedList<>();

        for (ScoreAddDto scoreDto : scoreDtos) {
            Score scoreToAdd = Score.builder()
                    //TODO po utworzeniu meczu przejechać po całej liście Score i dodać utowrzony mecz do pól Score'ów
//                    .match(matchRepository.findById(scoreDtos.get(i).getMatchId()).get())
                    .footballer(footballerRepository.findById(scoreDto.getFootballerId()).get())
                    .minuteScored(scoreDto.getMinuteScored())
                    .build();

            convertedScores.add(scoreToAdd);
        }
        return convertedScores;
    }

    //ScoreList refer back to MatchId
    private List<ScoreAddDto> listToDto(List<Score> scoreList) {
        List<ScoreAddDto> convertedScores = new LinkedList<>();

        for (Score score : scoreList) {
            ScoreAddDto scoreToAdd = ScoreAddDto.builder()
                    .matchId(score.getMatch().getId())
                    .footballerId(score.getFootballer().getId())
                    .minuteScored(score.getMinuteScored())
                    .build();
            convertedScores.add(scoreToAdd);
        }
        return convertedScores;
    }

    @Transactional
    public void deleteMatchById(@PathVariable int id) {
        if (matchRepository.existsById(id)) {
            matchRepository.deleteById(id);
        }
    }
}
