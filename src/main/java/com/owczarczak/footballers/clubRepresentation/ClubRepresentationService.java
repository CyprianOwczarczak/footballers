package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.footballer.FootballerService;
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

    @Autowired
    FootballerService footballerService;

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

    Optional<ClubRepresentationDto> getClubRepresentationById(@PathVariable Long id) {
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
    public void deleteClubRepresentation(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }

    public ClubRepresentation toEntity(ClubRepresentationAddDto representationDto) {
        Club optionalClub = clubRepository.findById(representationDto.getClubId()).orElseThrow(RuntimeException::new);
        validateFootballerList(footballerService.convertIdToEntityList(representationDto.getFootballersIdList()));

        return ClubRepresentation.builder()
                .club(optionalClub)
                .footballerList(footballerService.convertIdToEntityList(representationDto.getFootballersIdList()))
                .build();
    }

    public ClubRepresentationAddDto toRepresentationDto(ClubRepresentation representation) {
        return ClubRepresentationAddDto.builder()
                .id(representation.getId())
                .clubId(representation.getClub().getId())
                .footballersIdList(footballerService.convertEntityToIdList(representation.getFootballerList()))
                .build();
    }

    private void validateFootballerList(List<Footballer> footballerList) {
        for (Footballer footballer : footballerList) {
            if (footballerService.getFootballerById(footballer.getId()).isEmpty()) {
                throw new RuntimeException();
            }
        }
    }
}
