package com.owczarczak.footballers.match;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/matches")
public class MatchController {

    @Autowired
    MatchService service;

    @GetMapping("/")
    public List<MatchDto> getAllMatches() {
        return service.getAllMatches();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MatchDto> getMatchById(@PathVariable int id) {
        Optional<MatchDto> foundDtoOptional = service.getMatchById(id);
        if (foundDtoOptional.isEmpty()) {
            return notFound().build();
        } else {
            return ok(foundDtoOptional.get());
        }
    }

    @GetMapping("/byRefereeName/")
    List<MatchDto> findByRefereeName(@RequestParam("name") String name) {
        return service.findByRefereeName(name);
    }

    @PostMapping("/")
    ResponseEntity<?> addMatch(@RequestBody MatchAddDto newMatchDto) {
        if (service.repository.existsById(newMatchDto.getId())) {
            return ResponseEntity.badRequest().build();
        }

        ArrayList<String> errorList = returnErrorList(newMatchDto);
        if (!errorList.isEmpty()) {
            return ResponseEntity.badRequest().body(errorList);
        }

        MatchAddDto result = service.addMatch(newMatchDto);
        return ResponseEntity.created(URI.create("/" + result.getId()))
                .body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteMatch(@PathVariable int id) {
        service.deleteMatchById(id);
        return ok().build();
    }

    private ArrayList<String> returnErrorList(MatchAddDto newMatchDto) {
        ArrayList<String> errorList = new ArrayList<>();

        if (newMatchDto.getHost() == null) {
            errorList.add("You have to provide a host !");
        }
        if (newMatchDto.getGuest() == null) {
            errorList.add("You have to provide a guest !");
        }
        if (StringUtils.isEmpty(newMatchDto.getNameOfReferee())) {
            errorList.add("You have to provide a referee name !");
        }
        if (newMatchDto.getDate() == null) {
            errorList.add("You have to provide a date !");
        }
        return errorList;
    }
}
