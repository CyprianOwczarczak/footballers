package com.owczarczak.footballers.club;

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
public class ClubService {

    @Autowired
    ClubRepository repository;

    List<ClubDto> getAllClubs() {
        List<Club> clubList = repository.findAll();
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

    Optional<ClubDto> getClubById(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        } else {
            Club club = repository.getReferenceById(id);
            return Optional.ofNullable(ClubDto.builder()
                    .id(club.getId())
                    .created(club.getCreated())
                    .name(club.getName())
                    .build());
        }
    }

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

    @Transactional
    public void deleteClub(@PathVariable int id) {
        repository.deleteById(id);
    }
}

