package com.owczarczak.footballers.footballer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.*;

@RestController
@RequestMapping("/footballers")
public class FootballerController {

    @Autowired
    FootballerService service;

    @GetMapping("/")
    public List<FootballerDto> getAllFootballers() {
        return service.getAllFootballers();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<FootballerDto> getFootballerById(@PathVariable int id) {
        Optional<FootballerDto> foundDtoOptional = service.getFootballerById(id);
        if (foundDtoOptional.isEmpty()) {
            return notFound().build();
        } else {
            return ok(foundDtoOptional.get());
        }
    }

    @GetMapping(path = "/topXByHeight/")
    public List<FootballerDto> getHighest(@RequestParam(value = "pageNumber") int pageNumber,
                                          @RequestParam(value = "numberOfPlayers") int numberOfPlayers) {
        return service.getXHighestFootballers(pageNumber, numberOfPlayers);
    }

    @GetMapping("/byName/")
    public List<FootballerDto> getFootballersByName(@RequestParam(value = "name") String name) {
        return service.getFootballersByName(name);
    }

    @PostMapping("/")
    ResponseEntity<?> addFootballer(@RequestBody FootballerDto newFootballerDto) {

        if (service.existsByPesel(newFootballerDto.getPesel())) {
            return ResponseEntity.badRequest().build();
        } else if (newFootballerDto.getPesel() == null) {
            return ResponseEntity.badRequest().body("You have to provide a pesel !");
        } else if (newFootballerDto.getName() == null) {
            return ResponseEntity.badRequest().body("You have to provide a name !");
        } else if (newFootballerDto.getHeight() == 0) {
            return ResponseEntity.badRequest().body("You have to provide a footballer height !");
        } else {
            FootballerDto result = service.addFootballer(newFootballerDto);
            return ResponseEntity
                    .created(URI.create("/" + result.getId()))
                    .body(result);
        }
    }

    @PutMapping("/")
    ResponseEntity<?> updateFootballer(@RequestBody FootballerDto footballerToUpdate) {
        if (service.getFootballerById(footballerToUpdate.getId()).isEmpty()) {
            return notFound().build();
        } else if (footballerToUpdate.getPesel() == null) {
            return badRequest().body("You have to provide a pesel !");
        } else if (footballerToUpdate.getName() == null) {
            return badRequest().body("You have to provide a name !");
        } else if (footballerToUpdate.getHeight() == 0) {
            return badRequest().body("You have to provide a footballer height !");
        } else {
            Optional<FootballerDto> result = service.updateFootballer(footballerToUpdate.getId(), footballerToUpdate);
            return ok(result.get());
        }

    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteFootballer(@PathVariable int id) {
        service.deleteFootballer(id);
        return ok().build();
    }
}
