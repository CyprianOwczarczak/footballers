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

    //TODO DTO mają mieć buildery, i przepisywać ręcznie

    @Autowired
    FootballerRepository repository;

    public List<Footballer> getAllFootballers() {
        return repository.findAll();
    }

    public Optional<Footballer> getFootballerById(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        return repository.findById(id);
    }

    public List<FootballerDTO> get3HighestFootballers() {
        return repository.get3HighestFootballers();
    }

    public List<Footballer> getFootballersByName(String name) {
        return repository.findByName(name);
    }

    public Footballer addFootballer(@RequestBody @Valid Footballer footballerToAdd) {
        return repository.save(footballerToAdd);
    }

    public Optional<Footballer> updateFootballer(@PathVariable int id, @RequestBody Footballer footballerToUpdate) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        footballerToUpdate.setId(id);
        Footballer result = repository.save(footballerToUpdate);
        return Optional.of(result);
    }

    public boolean deleteFootballer(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }
}
