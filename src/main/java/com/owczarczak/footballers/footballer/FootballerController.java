package com.owczarczak.footballers.footballer;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.badRequest;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/footballers")
public class FootballerController {

    @Autowired
    private FootballerService service;

    @GetMapping("/")
    public List<FootballerDto> getAllFootballers() {
        return service.getAllFootballers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FootballerDto> getFootballerById(@PathVariable Long id) {
        Optional<FootballerDto> foundDtoOptional = service.getFootballerById(id);
        return foundDtoOptional.map(ResponseEntity::ok).orElseGet(() -> notFound().build());
    }

    @GetMapping(path = "/topXByHeight/")
    public List<FootballerDto> getHighest(@RequestParam(value = "pageNumber") Integer pageNumber,
                                          @RequestParam(value = "numberOfPlayers") Integer numberOfPlayers) {
        return service.getXHighestFootballers(pageNumber, numberOfPlayers);
    }

    @GetMapping("/byName/")
    public List<FootballerDto> getFootballersByName(@RequestParam(value = "name") String name) {
        return service.getFootballersByName(name);
    }

    @GetMapping("/mostMatchesPlayed")
    public List<FootballerDtoMostMatches> getFootballersWhoPlayedInMostMatches() {
        return service.getFootballersWhoPlayedInMostMatches();
    }

    @PostMapping("/")
    ResponseEntity<?> addFootballer(@RequestBody FootballerDto newFootballerDto) {
        if (service.existsByPesel(newFootballerDto.getPesel())) {
            return ResponseEntity.badRequest().build();
        }

        List<String> errorList = returnErrorList(newFootballerDto);
        if (!errorList.isEmpty()) {
            return ResponseEntity.badRequest().body(errorList);
        }
        FootballerDto result = service.addFootballer(newFootballerDto);
        return ResponseEntity
                .created(URI.create("/" + result.getId()))
                .body(result);
    }

    @PutMapping("/")
    ResponseEntity<?> updateFootballer(@RequestBody FootballerDto footballerToUpdate) {
        if (footballerToUpdate.getId() == null) {
            return badRequest().build();
        }

        List<String> errorList = returnErrorList(footballerToUpdate);
        if (!errorList.isEmpty()) {
            return badRequest().body(errorList);
        }

        if (service.getFootballerById(footballerToUpdate.getId()).isEmpty()) {
            return notFound().build();
        }

        FootballerDto result = service.updateFootballer(footballerToUpdate)
                .orElseThrow(RuntimeException::new);

        return ok(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteFootballer(@PathVariable Long id) {
        service.deleteFootballer(id);
        return ok().build();
    }

    private ArrayList<String> returnErrorList(FootballerDto newFootballerDto) {
        ArrayList<String> errorList = new ArrayList<>();

        if (StringUtils.isEmpty(newFootballerDto.getPesel())) {
            errorList.add("You have to provide a pesel !");
        }
        if (StringUtils.isEmpty(newFootballerDto.getName())) {
            errorList.add("You have to provide a name !");
        }
        if (newFootballerDto.getHeight() == null) {
            errorList.add("You have to provide height !");
        }
        return errorList;
    }

//    private ArrayList<String> returnErrorListUpdate(FootballerDto newFootballerDto) {
//        ArrayList<String> errorList = new ArrayList<>();
//
//        if (newFootballerDto.getId() == null) {
//            errorList.add("You have to provide an id !");
//        }
//
//        if (StringUtils.isEmpty(newFootballerDto.getPesel())) {
//            errorList.add("You have to provide a pesel !");
//        }
//        if (StringUtils.isEmpty(newFootballerDto.getName())) {
//            errorList.add("You have to provide a name !");
//        }
//        if (newFootballerDto.getHeight() == null) {
//            errorList.add("You have to provide height !");
//        }
//        return errorList;
//    }
}
