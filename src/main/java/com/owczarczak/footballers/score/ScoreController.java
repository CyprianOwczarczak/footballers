package com.owczarczak.footballers.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/scores")
public class ScoreController {
    @Autowired
    ScoreService service;

    @GetMapping("/getAverageGoals")
    public List<ScoreAvgGoalsDto> getAvgGoalsPerMatchForFootballer() {
        return service.getAvgGoalsPerMatchForFootballer();
    }
}
