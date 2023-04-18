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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ScoreControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ScoreRepository scoreRepository;
    @Autowired
    private FootballerRepository footballerRepository;
    @Autowired
    private ClubRepository clubRepository;
    @Autowired
    private ClubRepresentationRepository repRepository;
    @Autowired
    private MatchRepository matchRepository;

    @AfterEach
    void setup() {
        scoreRepository.deleteAll();
        matchRepository.deleteAll();
        repRepository.deleteAll();
        footballerRepository.deleteAll();
        clubRepository.deleteAll();
    }

    @Test
    void shouldGetAllScores() throws Exception {
        //given
        //Creating matches with ClubRepresentations
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());

        //Matches save the ClubRepresentations in by cascade
        List<ClubRepresentation> clubRepresentations = TestDataFactory.getRepresentationList1(clubList);
        List<Match> matchList = matchRepository.saveAll(TestDataFactory.getMatchList(clubRepresentations));

        // Creating Scores
        List<Footballer> footballerList = footballerRepository.saveAll(TestDataFactory.getFootballerList());
        scoreRepository.saveAll(TestDataFactory.getScoresList(matchList, footballerList));

        //when + then
        this.mockMvc.perform(get("/matches/"))
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
        ;
    }

    @Test
    void shouldGetScoreById() throws Exception {
        //given
        //Creating matches with ClubRepresentations
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        List<ClubRepresentation> clubRepresentations = TestDataFactory.getRepresentationList1(clubList);
        List<Match> matchList = matchRepository.saveAll(TestDataFactory.getMatchList(clubRepresentations));

        // Creating Scores
        List<Footballer> footballerList = footballerRepository.saveAll(TestDataFactory.getFootballerList());
        List<Score> scoreList = scoreRepository.saveAll(TestDataFactory.getScoresList(matchList, footballerList));
        int scoreId = scoreList.get(0).getId();

        //when + then
        this.mockMvc.perform(get("/scores/" + scoreId))
                .andDo(print())
                .andExpect(jsonPath("$").isMap());
    }

    @Test
    @DisplayName("Should get average goals per match for footballer")
    void shouldGetAvgGoalsPerMatchForFootballer() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        List<Footballer> footballerList = footballerRepository.saveAll(TestDataFactory.getFootballersWithRepresentationsList(clubList));

        Footballer footballer1 = new Footballer("100000", "AvgFootballer", 150);
        Footballer footballer2 = new Footballer("200000", "AvgFootballer2", 160);
        List<Footballer> avgFootballerList = List.of(footballer1, footballer2);
        footballerRepository.saveAll(avgFootballerList);

        List<ClubRepresentation> clubRepresentations = TestDataFactory.getRepresentationsWithFootballersList(clubList, footballerList, avgFootballerList);
        List<Match> matchList = matchRepository.saveAll(TestDataFactory.getMatchList(clubRepresentations));

        // Creating Scores
        scoreRepository.saveAll(TestDataFactory.getScoresList(matchList, footballerList));

        //when + then
        this.mockMvc.perform(get("/scores/getAverageGoals/"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)))
                .andDo(print());
    }

    @Test
    @DisplayName("Should add a score")
    void shouldAddScore() throws Exception {
        //given - adding matches and footballers the scores will refer to
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        List<ClubRepresentation> clubRepresentations = TestDataFactory.getRepresentationList1(clubList);
        List<Match> matchList = matchRepository.saveAll(TestDataFactory.getMatchList(clubRepresentations));
        List<Footballer> footballerList = footballerRepository.saveAll(TestDataFactory.getFootballerList());
        List<Score> scoreList = scoreRepository.saveAll(TestDataFactory.getScoresList(matchList, footballerList));

        int matchId = matchList.get(1).getId();
        int footballerId = footballerList.get(1).getId();

        String request = """
                {
                "matchId":%d,
                "footballerId":%d,
                "minuteScored":20
                }
                """.formatted(matchId, footballerId);

        //when + then
        this.mockMvc.perform(post("/scores/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isCreated(),
                        jsonPath("$").isMap());
    }

    @Test
    void shouldDeleteScoreById() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        List<ClubRepresentation> clubRepresentations = TestDataFactory.getRepresentationList1(clubList);
        List<Match> matchList = matchRepository.saveAll(TestDataFactory.getMatchList(clubRepresentations));
        List<Footballer> footballerList = footballerRepository.saveAll(TestDataFactory.getFootballerList());

        List<Score> scoreList = scoreRepository.saveAll(TestDataFactory.getScoresList(matchList, footballerList));
        int scoreId = scoreList.get(0).getId() + 2;

        //when + then
        this.mockMvc.perform(delete("/scores/" + scoreId))
                .andDo(print())
                .andExpect(status().isOk());

        List<Score> returnedScore = scoreRepository.findAll();
        assertEquals(10, returnedScore.get(0).getMinuteScored());
        assertEquals(20, returnedScore.get(1).getMinuteScored());
        assertEquals(40, returnedScore.get(2).getMinuteScored());
        assertEquals(50, returnedScore.get(3).getMinuteScored());
        assertEquals(60, returnedScore.get(4).getMinuteScored());
        assertEquals(5, scoreRepository.findAll().size());
    }

    @Test
    void shouldNotDeleteScoreById() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        List<ClubRepresentation> clubRepresentations = TestDataFactory.getRepresentationList1(clubList);
        List<Match> matchList = matchRepository.saveAll(TestDataFactory.getMatchList(clubRepresentations));
        List<Footballer> footballerList = footballerRepository.saveAll(TestDataFactory.getFootballerList());

        List<Score> scoreList = scoreRepository.saveAll(TestDataFactory.getScoresList(matchList, footballerList));
        int scoreId = scoreList.get(0).getId() + 1000;

        //when + then
        this.mockMvc.perform(delete("/scores/" + scoreId))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(6, scoreRepository.findAll().size());
    }
}
