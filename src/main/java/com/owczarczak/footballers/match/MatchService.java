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
    ClubRepresentationService representationService;

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
                .build();
        Match savedEntity = matchRepository.save(newMatch);

        return MatchAddDto.builder()
                .id(savedEntity.getId())
                .guestRepresentation(representationService.toRepresentationDto(savedEntity.getGuest()))
                .hostRepresentation(representationService.toRepresentationDto(savedEntity.getHost()))
                .nameOfReferee(savedEntity.getNameOfReferee())
                .date(savedEntity.getDate())
                .build();
    }

    @Transactional
    public void deleteMatchById(@PathVariable int id) {
        if (matchRepository.existsById(id)) {
            matchRepository.deleteById(id);
        }
    }
}
