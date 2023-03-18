package com.owczarczak.footballers.match;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {

    @Autowired
    MatchRepository repository;

    List<MatchDto> findByRefereeName(String nameOfReferee) {
        List<Match> matchList = repository.findByRefereeName(nameOfReferee);
        List<MatchDto> dtos = new LinkedList<>();
        for (Match match : matchList) {
            MatchDto dtoToBeAdded = MatchDto.builder()
                    .id(match.getId())
                    .hostClubName(match.getHost().getClub().getName()) //fixme Check if here we don't have some error
                    .guestClubName(match.getGuest().getClub().getName())
                    .nameOfReferee(match.getNameOfReferee())
                    .date(match.getDate())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }
//        return MatchDto.builder()
//                .id(matchList.getId())
//                .hostClubName(matchList.getHost().getClub().getName())
//                .guestClubName(matchList.getGuest().getClub().getName())
//                .nameOfReferee(matchList.getNameOfReferee())
//                .date(matchList.getDate())
//                .build();
//    }
}
