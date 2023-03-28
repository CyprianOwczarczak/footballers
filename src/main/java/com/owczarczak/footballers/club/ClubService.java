package com.owczarczak.footballers.club;

import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.footballer.FootballerDto;
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

    public ClubDto addClub(ClubDto clubToBeAdded) {
        Club newClub = Club.builder()
                .id(clubToBeAdded.getId())
                .name(clubToBeAdded.getName())
                .created(clubToBeAdded.getCreated())
                .build();

        Club savedEntity = repository.save(newClub);

        return ClubDto.builder()
                .id(savedEntity.getId())
                .name(savedEntity.getName())
                .created(savedEntity.getCreated())
                .build();
    }

    @Transactional
    public void deleteClubById(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}

