package com.owczarczak.footballers.footballer;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FootballerService {

    @Autowired
    FootballerRepository repository;

    public List<Footballer> getAllFootballers() {
        return repository.findAll();
    }

    public Optional<Footballer> getFootballer(@PathVariable int id) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        return repository.findById(id);
    }

    public List<Footballer> get3HighestFootballers() {
        return repository.get3HighestFootballers();
    }

    public List<Footballer> getFootballersByName(String name) {
        return repository.getFootballersByName(name);
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
