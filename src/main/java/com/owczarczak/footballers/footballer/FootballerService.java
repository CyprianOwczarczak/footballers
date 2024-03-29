package com.owczarczak.footballers.footballer;

import com.owczarczak.footballers.club.Club;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.transaction.Transactional;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FootballerService {

    @Autowired
    private FootballerRepository repository;

    public List<FootballerDto> getAllFootballers() {
        List<Footballer> footballersList = repository.findAll();
        List<FootballerDto> dtos = new LinkedList<>();
        for (Footballer footballer : footballersList) {
            FootballerDto dtoToBeAdded = FootballerDto.builder()
                    .id(footballer.getId())
                    .pesel(footballer.getPesel())
                    .name(footballer.getName())
                    .height(footballer.getHeight())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    public Optional<FootballerDto> getFootballerById(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        } else {
            Footballer footballer = repository.getReferenceById(id);
            return Optional.ofNullable(FootballerDto.builder()
                    .id(footballer.getId())
                    .pesel(footballer.getPesel())
                    .name(footballer.getName())
                    .height(footballer.getHeight())
                    .build());
        }
    }

    public List<FootballerDto> getXHighestFootballers(Integer pageNumber, Integer numberOfPlayers) {
        Pageable pageable = PageRequest.of(pageNumber, numberOfPlayers);
        Page<Footballer> footballersList = repository.findAllByOrderBxyHeightDesc(pageable);

        List<FootballerDto> dtos = new LinkedList<>();
        for (Footballer footballer : footballersList) {
            FootballerDto dtoToBeAdded = FootballerDto.builder()
                    .id(footballer.getId())
                    .pesel(footballer.getPesel())
                    .name(footballer.getName())
                    .height(footballer.getHeight())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    public List<FootballerDto> getFootballersByName(String name) {
        List<Footballer> footballersList = repository.findByName(name);
        List<FootballerDto> dtos = new LinkedList<>();
        for (Footballer footballer : footballersList) {
            FootballerDto dtoToBeAdded = FootballerDto.builder()
                    .id(footballer.getId())
                    .pesel(footballer.getPesel())
                    .name(footballer.getName())
                    .height(footballer.getHeight())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    public FootballerDto addFootballer(FootballerDto footballerToBeAdded) {
        Footballer newFootballer = Footballer.builder()
                .pesel(footballerToBeAdded.getPesel())
                .name(footballerToBeAdded.getName())
                .height(footballerToBeAdded.getHeight())
                .build();
        Footballer savedEntity = repository.save(newFootballer);

        return FootballerDto.builder()
                .id(savedEntity.getId())
                .pesel(savedEntity.getPesel())
                .name(savedEntity.getName())
                .height(savedEntity.getHeight())
                .build();
    }

    public Optional<FootballerDto> updateFootballer(FootballerDto footballerToBeUpdated) {
        Footballer updatedFootballer = Footballer.builder()
                .id(footballerToBeUpdated.getId())
                .pesel(footballerToBeUpdated.getPesel())
                .name(footballerToBeUpdated.getName())
                .height(footballerToBeUpdated.getHeight())
                .build();
        Footballer savedEntity = repository.save(updatedFootballer);
        return Optional.of(FootballerDto.builder()
                .id(savedEntity.getId())
                .pesel(savedEntity.getPesel())
                .name(savedEntity.getName())
                .height(savedEntity.getHeight())
                .build());
    }

    //Returns FootballerDtoMostMatches with id only
    public List<FootballerDtoMostMatches> getFootballersWhoPlayedInMostMatches() {
        List<Long[]> footballersList = repository.whichFootballersPlayedInMostMatches();
        List<FootballerDtoMostMatches> dtos = new LinkedList<>();
        for (Long[] longs : footballersList) {
            FootballerDtoMostMatches dtoToBeAdded = FootballerDtoMostMatches.builder()
                    .id(longs[0])
                    .representationSize(Math.toIntExact(longs[1]))
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    @Transactional
    public void deleteFootballer(@PathVariable Long id) {
        repository.deleteAllById(id);
    }

    public boolean existsByPesel(String pesel) {
        return repository.existsByPesel(pesel);
    }

    public List<Footballer> convertIdToEntityList(List<Long> idList) {
        List<Footballer> listToReturn = new LinkedList<>();

        for (Long id : idList) {
            Footballer optionalFootballer = repository.findById(id).orElseThrow(RuntimeException::new);

            listToReturn.add(optionalFootballer);
        }
        return listToReturn;
    }

    public List<Long> convertEntityToIdList(List<Footballer> footballerList) {
        List<Long> listToReturn = new LinkedList<>();

        for (Footballer footballer : footballerList) {
            Long id = footballer.getId();
            listToReturn.add(id);
        }
        return listToReturn;
    }
}
