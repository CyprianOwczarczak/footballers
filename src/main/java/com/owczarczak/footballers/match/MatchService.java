package com.owczarczak.footballers.match;

import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.footballer.FootballerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MatchService {

    @Autowired
    MatchRepository repository;

    MatchDto findByRefereeName(String nameOfReferee) {
        Match match = repository.findByRefereeName(nameOfReferee);
        return MatchDto.builder()
                .id(match.getId())
                .hostClubName(match.getHost().getClub().getName())
                .guestClubName(match.getGuest().getClub().getName())
                .nameOfReferee(match.getNameOfReferee())
                .date(match.getDate())
                .build();
    }
}
