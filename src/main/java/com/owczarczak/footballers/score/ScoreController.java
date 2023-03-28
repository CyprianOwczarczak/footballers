package com.owczarczak.footballers.score;

import com.owczarczak.footballers.club.ClubDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/scores")
public class ScoreController {
    @Autowired
    ScoreService service;

    @GetMapping("/")
    public List<ScoreDto> getAllScores() {
        return service.getAllScores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScoreDto> getScoreById(@PathVariable int id) {
        Optional<ScoreDto> foundDtoOptional = service.getScoreById(id);
        if (foundDtoOptional.isEmpty()) {
            return notFound().build();
        } else {
            return ok(foundDtoOptional.get());
        }
    }

    @GetMapping("/getAverageGoals/")
    public List<ScoreAvgGoalsDto> getAvgGoalsPerMatchForFootballer() {
        return service.getAvgGoalsPerMatchForFootballer();
    }

    @GetMapping("/getAverageNew/")
    public List<ScoreNewDto> getAvgNew() {
        return service.getAvgNew();
    }

    //TODO add Post method

    @DeleteMapping("/{id}")
    ResponseEntity deleteMatch(@PathVariable int id) {
        service.deleteMatchById(id);
        return ok().build();
    }
}
