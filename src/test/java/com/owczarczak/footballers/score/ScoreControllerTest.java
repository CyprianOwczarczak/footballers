package com.owczarczak.footballers.score;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class ScoreControllerTest {

    @Autowired
    ScoreService service;

    @Test
    void shouldReturnRepository() {
        List<ScoreAvgGoalsDto> returned = service.getAvgGoalsPerMatchForFootballer();
    }
}
