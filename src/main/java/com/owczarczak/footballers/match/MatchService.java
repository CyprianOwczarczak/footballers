package com.owczarczak.footballers.match;

import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.footballer.FootballerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import javax.xml.xpath.XPathVariableResolver;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MatchService {

    @Autowired
    MatchRepository repository;

    List<MatchDto> getAllMatches() {
        List<Match> matchList = repository.findAll();
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
        if (!repository.existsById(id)) {
            return Optional.empty();
        } else {
            Match match = repository.getReferenceById(id);
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
        List<Match> matchList = repository.findByRefereeName(nameOfReferee);
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

//    public MatchAddDto addMatch(MatchAddDto matchToBeAdded) {
//        Match newMatch = Match.builder()
//                .id(matchToBeAdded.getId())
//                .guest(matchToBeAdded.getGuest())
//                .host(matchToBeAdded.getHost())
//                .nameOfReferee(matchToBeAdded.getNameOfReferee())
//                .date(matchToBeAdded.getDate())
//                .scores(matchToBeAdded.getScores())
//                .build();
//        Match savedEntity = repository.save(newMatch);
//
//        return MatchAddDto.builder()
//                .id(savedEntity.getId())
//                .guest(savedEntity.getGuest())
//                .host(savedEntity.getHost())
//                .nameOfReferee(savedEntity.getNameOfReferee())
//                .date(savedEntity.getDate())
//                .scores(savedEntity.getScores())
//                .build();
//    }

    @Transactional
    public void deleteMatchById(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}
