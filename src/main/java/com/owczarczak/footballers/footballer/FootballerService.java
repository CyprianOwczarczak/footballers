package com.owczarczak.footballers.footballer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
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

    public List<FootballerDto> get3HighestFootballers() {
        List<Footballer> footballersList = repository.findTop3ByOrderByHeightDesc();
        List<FootballerDto> dtos = new LinkedList<>();
        for (Footballer footballer : footballersList) {
            FootballerDto dtoToBeAdded = FootballerDto.builder()
                    .id(footballer.getId())
                    .name(footballer.getName())
                    .pesel(footballer.getPesel())
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
                    .name(footballer.getName())
                    .pesel(footballer.getPesel())
                    .goals(footballer.getGoals())
                    .height(footballer.getHeight())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    public FootballerDto addFootballer(@RequestBody @Valid FootballerDto footballerToBeAdded) {
//        Footballer newFootballer =
//                return repository.save(footballerToBeAdded);
//        Footballer footballer = repository.save(newFootballer);
//        return mapper.toDto(footballer);
        throw new UnsupportedOperationException();

    }

    public Optional<FootballerDto> updateFootballer(@PathVariable int id,
                                                    @RequestBody FootballerDto footballerToUpdate) {
//        if (!repository.existsById(id)) {
//            return Optional.empty();
//        }
//        footballerToUpdate.setId(id);
//        Footballer updatedFootballer = mapper.toEntity(footballerToUpdate);
//        Footballer footballer = repository.save(updatedFootballer);
//        return Optional.ofNullable(mapper.toDto(updatedFootballer));
        throw new UnsupportedOperationException();
    }

    public boolean deleteFootballer(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
