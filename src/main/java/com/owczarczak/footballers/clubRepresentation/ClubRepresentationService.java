package com.owczarczak.footballers.clubRepresentation;

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
}
