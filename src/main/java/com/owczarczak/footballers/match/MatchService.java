package com.owczarczak.footballers.match;

import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.time.Instant;
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
        ClubRepresentation guest = representationRepository.findById(matchToBeAdded.getGuestId()).get();
        ClubRepresentation host = representationRepository.findById(matchToBeAdded.getHostId()).get();

        Match newMatch = Match.builder()
                .id(matchToBeAdded.getId())
                .guest(guest)
                .host(host)
                .nameOfReferee(matchToBeAdded.getNameOfReferee())
                .date(matchToBeAdded.getDate())
//                .scores(matchToBeAdded.)
                .build();
        Match savedEntity = matchRepository.save(newMatch);

        return MatchAddDto.builder()
                .id(savedEntity.getId())
                .guestId(savedEntity.getGuest().getId())
                .hostId(savedEntity.getHost().getId())
                .nameOfReferee(savedEntity.getNameOfReferee())
                .date(savedEntity.getDate())
//                .scores(savedEntity.getScores())
                .build();
    }

    @Transactional
    public void deleteMatchById(@PathVariable int id) {
        if (matchRepository.existsById(id)) {
            matchRepository.deleteById(id);
        }
    }
}
