package com.owczarczak.footballers.footballer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/footballers")
public class FootballerController {

    @Autowired
    FootballerService service;

    @GetMapping
    public String index(Footballer footballer) {
        return "Greetings !";
    }

    @GetMapping("/")
    public List<Footballer> getAllFootballers() {
        return service.getAllFootballers();
    }

    @GetMapping("/{id}")
    public Optional<Footballer> getFootballerById(@PathVariable int id) {
        return service.getFootballerById(id);
    }

    @GetMapping("/top3ByHeight")
    public List<Footballer> getHighest() {
        return service.get3HighestFootballers();
    }

    @GetMapping("/{name}")
    public List<Footballer> getFootballersByName(@RequestParam String name) {
        return service.getFootballersByName(name);
    }

    @PostMapping
    ResponseEntity<Footballer> addFootballer(@RequestBody @Validated Footballer footballerToAdd) {
        Footballer result = service.addFootballer(footballerToAdd);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PutMapping("/{id}")
    Optional<Footballer> updateFootballer(@PathVariable int id, @RequestBody @Validated Footballer footballerToUpdate) {
        return service.updateFootballer(id, footballerToUpdate);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Footballer> deleteFootballer(@PathVariable int id) {
        if (service.deleteFootballer(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
