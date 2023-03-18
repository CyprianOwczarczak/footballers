package com.owczarczak.footballers.score;

import com.owczarczak.footballers.TestDataFactory;
import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.footballer.FootballerRepository;
import com.owczarczak.footballers.match.Match;
import com.owczarczak.footballers.match.MatchRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ScoreControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ScoreRepository scoreRepository;
    @Autowired
    FootballerRepository footballerRepository;
    @Autowired
    ClubRepository clubRepository;
    @Autowired
    ClubRepresentationRepository repRepository;
    @Autowired
    MatchRepository matchRepository;

    @Autowired
    ScoreService service;

    @BeforeEach
    void setup2() {
        scoreRepository.deleteAll();
        footballerRepository.deleteAll();
        matchRepository.deleteAll();
        repRepository.deleteAll();
        clubRepository.deleteAll();
    }

    @AfterEach
    void setup() {
        scoreRepository.deleteAll();
        footballerRepository.deleteAll();
        matchRepository.deleteAll();
        repRepository.deleteAll();
        clubRepository.deleteAll();
    }

    @Test
    void shouldGetAvgGoalsPerMatchForFootballer() throws Exception {
        //footballer, score, match , clubRepresentation, clubRepresentation_footballer join table

        //Creating matches with ClubRepresentations
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());

        //Matches save the ClubRepresentations in by cascade
        List<ClubRepresentation> clubRepresentations = TestDataFactory.getRepresentationList1(clubList);
        List<Match> matchList = matchRepository.saveAll(TestDataFactory.getMatchList(clubRepresentations));

        // Creating Scores
        List<Footballer> footballerList = footballerRepository.saveAll(TestDataFactory.getFootballerList());
        scoreRepository.saveAll(TestDataFactory.getScoresList(matchList, footballerList));

        this.mockMvc.perform(get("/scores/getAverageGoals"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)))
                .andExpect(jsonPath("$[0]", is(2)))
                .andDo(print());
    }
}
