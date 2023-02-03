package com.owczarczak.footballers.footballer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FootballerService {

    private final FootballerMapper mapper;

    @Autowired
    FootballerRepository repository;

    public List<FootballerDto> getAllFootballers() {
        List<Footballer> footballersList = repository.findAll();
        return mapper.toDto(footballersList);
    }

    public Optional<FootballerDto> getFootballerById(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        Optional<Footballer> footballer = repository.findById(id);
        return Optional.ofNullable(mapper.toDto(footballer));
    }

    public List<FootballerDto> get3HighestFootballers() {
        List<Footballer> footballersList = repository.get3HighestFootballers();
        return mapper.toDto(footballersList);
    }

    public List<FootballerDto> getFootballersByName(String name) {
        List<Footballer> footballerList = repository.findByName(name);
        return mapper.toDto(footballerList);
    }

    public FootballerDto addFootballer(@RequestBody @Valid FootballerDto footballerToAdd) {
        Footballer newFootballer = mapper.toEntity(footballerToAdd);
        Footballer footballer = repository.save(newFootballer);
        return mapper.toDto(footballer);
    }

    public Optional<FootballerDto> updateFootballer(@PathVariable int id, @RequestBody FootballerDto footballerToUpdate) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        footballerToUpdate.setId(id);
        Footballer updatedFootballer = mapper.toEntity(footballerToUpdate);
        Footballer footballer = repository.save(updatedFootballer);
        return Optional.ofNullable(mapper.toDto(updatedFootballer));
    }

    public boolean deleteFootballer(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
