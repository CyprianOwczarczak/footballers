package com.owczarczak.footballers.score;

import com.owczarczak.footballers.footballer.FootballerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/scores")
public class ScoreController {
    @Autowired
    private ScoreService service;

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

    @PostMapping("/")
    ResponseEntity<?> addScore(@RequestBody ScoreAddDto newScoreDto) {
        ScoreAddDto result = service.addScore(newScoreDto);

        return ResponseEntity
                .created(URI.create("/" + result.getId()))
                .body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity deleteMatch(@PathVariable int id) {
        service.deleteMatchById(id);
        return ok().build();
    }
}
