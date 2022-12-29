package com.owczarczak.footballers.controller;

import com.owczarczak.footballers.model.Footballer;
import com.owczarczak.footballers.repository.FootballerRepository;
import jakarta.validation.Valid;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/footballers")
@Getter
public class FootballerController {
//    @Autowired
    private FootballerRepository repository;

    @GetMapping
    public String index(Footballer footballer) {
        return "Greetings !";
    }

    @GetMapping("/")
    ResponseEntity<List<Footballer>> getAllPlayers() {
        return ResponseEntity.ok(repository.getAllPlayers());
    }

//    @PostMapping
//    ResponseEntity<Footballer> addFootballer(@RequestBody @Valid Footballer footballerToAdd) {
//        Footballer result = repository.addFootballer(footballerToAdd);
//        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
//    }
}
