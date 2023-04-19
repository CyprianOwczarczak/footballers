package com.owczarczak.footballers.match;

import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationDto;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
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
//        ClubRepresentation guest = representationRepository.findById(matchToBeAdded.getGuestId()).get();
//        ClubRepresentation host = representationRepository.findById(matchToBeAdded.getHostId()).get();


        Match newMatch = Match.builder()
                .id(matchToBeAdded.getId())
                .guest(convertRepresentationDto(matchToBeAdded.getGuestRepresentation()))
                .host(convertRepresentationDto(matchToBeAdded.getHostRepresentation()))
                .nameOfReferee(matchToBeAdded.getNameOfReferee())
                .date(matchToBeAdded.getDate())
                //fixme
                .scores(matchToBeAdded.getScoresList())
                .build();
        Match savedEntity = matchRepository.save(newMatch);

        return MatchAddDto.builder()
                .id(savedEntity.getId())
                .guestRepresentation(savedEntity.getGuest())
                .hostRepresentation(savedEntity.getHost().getId())
                .nameOfReferee(savedEntity.getNameOfReferee())
                .date(savedEntity.getDate())
//                .scores(savedEntity.getScores())
                .build();
    }

    private ClubRepresentation convertRepresentationDto(ClubRepresentationDto representationDto) {
        return ClubRepresentation.builder()
                .club(representationRepository.findById(representationDto.getId()).get().getClub())
                .footballerList(representationDto.getFootballerList())
                .build();
    }

    @Transactional
    public void deleteMatchById(@PathVariable int id) {
        if (matchRepository.existsById(id)) {
            matchRepository.deleteById(id);
        }
    }
}
