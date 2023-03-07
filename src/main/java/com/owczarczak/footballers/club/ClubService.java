package com.owczarczak.footballers.club;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubService {

    @Autowired
    ClubRepository repository;

    List<ClubDto> getAllClubsWhichPlayedMoreThan3Matches() {
        List<Club> clubList = repository.getAllClubsWhichPlayedMoreThan3Matches();
        List<ClubDto> dtos = new LinkedList<>();
        for (Club club : clubList) {
            ClubDto dtoToBeAdded = ClubDto.builder()
                    .id(club.getId())
                    .name(club.getName())
                    .created(club.getCreated())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }
}

