package com.owczarczak.footballers.footballer;

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
    FootballerRepository repository;

    public List<FootballerDto> getAllFootballers() {
        List<Footballer> footballersList = repository.findAll();
        List<FootballerDto> dtos = new LinkedList<>();
        for (Footballer footballer : footballersList) {
            FootballerDto dtoToBeAdded = FootballerDto.builder()
                    .id(footballer.getId())
                    .pesel(footballer.getPesel())
                    .name(footballer.getName())
                    .club(footballer.getClub())
                    .goals(footballer.getGoals())
                    .height(footballer.getHeight())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    public Optional<FootballerDto> getFootballerById(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        } else {
            Footballer footballer = repository.getReferenceById(id);
            return Optional.ofNullable(FootballerDto.builder()
                    .id(footballer.getId())
                    .pesel(footballer.getPesel())
                    .name(footballer.getName())
                    .club(footballer.getClub())
                    .goals(footballer.getGoals())
                    .height(footballer.getHeight())
                    .build());
        }
    }

    public List<FootballerDto> getXHighestFootballers(int pageNumber, int numberOfPlayers) {
        Pageable pageable = PageRequest.of(pageNumber, numberOfPlayers);
        Page<Footballer> footballersList = repository.findAllByOrderByHeightDesc(pageable);

        List<FootballerDto> dtos = new LinkedList<>();
        for (Footballer footballer : footballersList) {
            FootballerDto dtoToBeAdded = FootballerDto.builder()
                    .id(footballer.getId())
                    .pesel(footballer.getPesel())
                    .name(footballer.getName())
                    .club(footballer.getClub())
                    .goals(footballer.getGoals())
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
                    .club(footballer.getClub())
                    .goals(footballer.getGoals())
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
                .club(footballerToBeAdded.getClub())
                .goals(footballerToBeAdded.getGoals())
                .height(footballerToBeAdded.getHeight())
                .build();
        Footballer savedEntity = repository.save(newFootballer);
        return FootballerDto.builder()
                .id(savedEntity.getId())
                .pesel(savedEntity.getPesel())
                .name(savedEntity.getName())
                .club(savedEntity.getClub())
                .goals(savedEntity.getGoals())
                .height(savedEntity.getHeight())
                .build();
    }

    public Optional<FootballerDto> updateFootballer(int id, FootballerDto footballerToBeUpdated) {
        Footballer updatedFootballer = Footballer.builder()
                .id(footballerToBeUpdated.getId())
                .pesel(footballerToBeUpdated.getPesel())
                .name(footballerToBeUpdated.getName())
                .club(footballerToBeUpdated.getClub())
                .goals(footballerToBeUpdated.getGoals())
                .height(footballerToBeUpdated.getHeight())
                .build();
        Footballer savedEntity = repository.save(updatedFootballer);
        return Optional.of(FootballerDto.builder()
                .id(savedEntity.getId())
                .pesel(savedEntity.getPesel())
                .name(savedEntity.getName())
                .club(savedEntity.getClub())
                .goals(savedEntity.getGoals())
                .height(savedEntity.getHeight())
                .build());
    }

@Transactional
    public void deleteFootballer(@PathVariable int id) {
//        repository.deleteById();
        repository.deleteAllById(id);
    }

    public boolean existsByPesel(String pesel) {
        return repository.existsByPesel(pesel);
    }
}
