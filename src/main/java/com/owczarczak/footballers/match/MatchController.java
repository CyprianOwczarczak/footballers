package com.owczarczak.footballers.match;

import com.owczarczak.footballers.score.ScoreDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/byRefereeName/{name}")
    List<MatchDto> findByRefereeName(@RequestParam("name") String name) {
        return service.findByRefereeName(name);
    }
}
