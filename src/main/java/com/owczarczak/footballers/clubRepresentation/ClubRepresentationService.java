package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubDto;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.footballer.FootballerDto;
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
public class ClubRepresentationService {

    @Autowired
    private ClubRepresentationRepository repository;

    @Autowired
    ClubRepository clubRepository;

    public List<ClubRepresentationDto> getAllClubRepresentations() {
        List<ClubRepresentation> clubRepresentationList = repository.findAll();
        List<ClubRepresentationDto> dtos = new LinkedList<>();
        for (ClubRepresentation representation : clubRepresentationList) {
            ClubRepresentationDto dtoToBeAdded = ClubRepresentationDto.builder()
                    .id(representation.getId())
                    .clubId(representation.getClub().getId())
                    .clubName(representation.getClub().getName())
                    .footballerList(representation.getFootballerList())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    Optional<ClubRepresentationDto> getClubRepresentationById(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        } else {
            ClubRepresentation clubRepresentation = repository.getReferenceById(id);
            return Optional.ofNullable(ClubRepresentationDto.builder()
                    .id(clubRepresentation.getId())
                    .clubId(clubRepresentation.getClub().getId())
                    .clubName(clubRepresentation.getClub().getName())
                    .footballerList(clubRepresentation.getFootballerList())
                    .build());
        }
    }

    @Transactional
    public void deleteClubRepresentation(@PathVariable int id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    //TODO Divide those methods into different Services

//    ✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓✓
    public ClubRepresentation toEntity(ClubRepresentationAddDto representationDto) {
        return ClubRepresentation.builder()
                //TODO obsłużyć NullPointerException z Optionala (orElseThrow)
                .club(clubRepository.findById(representationDto.getClub().getId()).get())
                .footballerList(toFootballerEntityList(representationDto.getFootballerList()))
                .build();
    }

    public List<Footballer> toFootballerEntityList(List<FootballerDto> footballerDtos) {
        List<Footballer> convertedFootballers = new LinkedList<>();
        for (FootballerDto footballerDto : footballerDtos) {
            Footballer footballerToAdd = Footballer.builder()
                    .pesel(footballerDto.getPesel())
                    .name(footballerDto.getName())
                    .height(footballerDto.getHeight())
                    .build();
            convertedFootballers.add(footballerToAdd);
        }
        return convertedFootballers;
    }

    //Convert Club representation without FootballerList
    public ClubRepresentationAddDto toRepresentationDto(ClubRepresentation representation) {
        return ClubRepresentationAddDto.builder()
                .id(representation.getId())
                .club(toClubDto(representation.getClub()))
                .build();
    }
//  ✓✓✓✓✓✓✓✓
    public ClubDto toClubDto(Club club) {
        return ClubDto.builder()
                .name(club.getName())
                .created(club.getCreated())
                .build();
    }
}
