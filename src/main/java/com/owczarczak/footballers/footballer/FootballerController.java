package com.owczarczak.footballers.footballer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/footballers")
public class FootballerController {

    @Autowired
    FootballerService service;

    @GetMapping("/")
    public List<FootballerDto> getAllFootballers() {

        return service.getAllFootballers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FootballerDto> getFootballerById(@PathVariable int id) {
        Optional<FootballerDto> foundDtoOptional = service.getFootballerById(id);
        if (foundDtoOptional.isEmpty()) {
            return notFound().build();
        } else {
            return ok(foundDtoOptional.get());
        }
    }

    @GetMapping("/top3ByHeight")
    public List<FootballerDto> getHighest() {
        return service.get3HighestFootballers();
    }

    @GetMapping("/{name}")
    public List<FootballerDto> getFootballersByName(@RequestParam String name) {
        return service.getFootballersByName(name);
    }

    @PostMapping
    ResponseEntity<FootballerDto> addFootballer(@RequestBody @Validated FootballerDto footballerToAdd) {
        FootballerDto result = service.addFootballer(footballerToAdd);
        return ResponseEntity
                .created(URI.create("/" + result.getId()))
                .body(result);
    }

    @PutMapping("/{id}")
    ResponseEntity<FootballerDto> updateFootballer(@PathVariable int id, @RequestBody @Validated FootballerDto footballerToUpdate) {
        if (service.getFootballerById(id).isEmpty()) {
            return notFound().build();
        }
        return ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<FootballerDto> deleteFootballer(@PathVariable int id) {
        if (service.deleteFootballer(id)) {
            return ok().build();
        }
        return notFound().build();
    }
}
