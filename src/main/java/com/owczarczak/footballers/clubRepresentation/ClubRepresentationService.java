package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.footballer.FootballerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClubRepresentationService {

    @Autowired
    ClubRepresentationRepository repository;

    public List<ClubRepresentationDto> getAllClubRepresentations() {
        List<ClubRepresentation> clubRepresentationList = repository.findAll();
        List<ClubRepresentationDto> dtos = new LinkedList<>();
        for (ClubRepresentation representation : clubRepresentationList) {
            ClubRepresentationDto dtoToBeAdded = ClubRepresentationDto.builder()
                    .id(representation.getId())
                    .clubName(representation.getClub().getName())
                    .footballerList(representation.getFootballerList())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }
}
