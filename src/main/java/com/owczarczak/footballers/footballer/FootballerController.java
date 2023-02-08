package com.owczarczak.footballers.footballer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping(path = "/release/{id}", headers = {"version", "git_tag"}, params = "name")
    public String testMethod(@PathVariable(value = "id") int id,
                             @RequestHeader(value = "version") String version,
                             @RequestHeader(value = "git_tag") String gitTag,
                             @RequestParam(value = "name") String name) {
        System.out.println(id);
        System.out.println(version);
        System.out.println(gitTag);
        System.out.println(name);
        return version;
    }

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
        return service.getNewHighest(pageNumber, numberOfPlayers);
    }

    @GetMapping("/byName/")
    public List<FootballerDto> getFootballersByName(@RequestParam(value = "name") String name) {
        return service.getFootballersByName(name);
    }

    @PostMapping("/")
    ResponseEntity<FootballerDto> addFootballer(@RequestBody FootballerDto newFootballerDto) {
        FootballerDto result = service.addFootballer(newFootballerDto);
        return ResponseEntity
                .created(URI.create("/" + result.getId()))
                .body(result);
    }

    @PutMapping("/")
    ResponseEntity<FootballerDto> updateFootballer(@RequestBody FootballerDto footballerToUpdate) {
        if (service.getFootballerById(footballerToUpdate.getId()).isEmpty()) {
            return notFound().build();
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

    //TODO
    //Prezentacja porządna -> Adnotacje z przykładami w kodzie moimi działającymi
    //Przykładowy program od zera któy weźmie obiekt klasy dziedziczące po tym samym i powie kto jest jego autorem (każdej klasy) (imie nazwisko data utworzenia,
//    stworzyć trzy klasy które mają różnych autorów (@Author)
    //Java 8 annotation tutorial --> Creating custom annotation in Java z Baeldunga
}
