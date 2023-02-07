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
                    .name(footballer.getName())
                    .pesel(footballer.getPesel())
                    .goals(footballer.getGoals())
                    .height(footballer.getHeight())
                    .build();
            dtos.add(dtoToBeAdded);
        }
        return dtos;
    }

    public Optional<FootballerDto> getFootballerById(@PathVariable int id) {
        throw new UnsupportedOperationException();
//        if (!repository.existsById(id)) {
//            return Optional.empty();
//        }
//        Optional<Footballer> footballer = repository.findById(id);
//        return Optional.ofNullable(mapper.toDto(footballer));
    }

    public List<FootballerDto> get3HighestFootballers() {
        throw new UnsupportedOperationException();
//        List<Footballer> footballersList = repository.get3HighestFootballers();
//        return mapper.toDto(footballersList);
    }

    public List<FootballerDto> getFootballersByName(String name) {
        throw new UnsupportedOperationException();
//        List<Footballer> footballerList = repository.findByName(name);
//        return mapper.toDto(footballerList);
    }

    public FootballerDto addFootballer(@RequestBody @Valid FootballerDto footballerToAdd) {
//        Footballer newFootballer = mapper.toEntity(footballerToAdd);
//        Footballer footballer = repository.save(newFootballer);
//        return mapper.toDto(footballer);
        throw new UnsupportedOperationException();
    }

    public Optional<FootballerDto> updateFootballer(@PathVariable int id, @RequestBody FootballerDto footballerToUpdate) {
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
//        if (!repository.existsById(id)) {
//            return false;
//        }
//        repository.deleteById(id);
//        return true;
        throw new UnsupportedOperationException();
    }
}
