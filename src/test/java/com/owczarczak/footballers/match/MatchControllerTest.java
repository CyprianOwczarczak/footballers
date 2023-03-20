package com.owczarczak.footballers.match;

import com.owczarczak.footballers.TestDataFactory;
import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
public class MatchControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    ClubRepresentationRepository representationRepository;

    @Autowired
    ClubRepository clubRepository;

    @BeforeEach
    void setup2() {
        matchRepository.deleteAll();
        representationRepository.deleteAll();
        clubRepository.deleteAll();
    }

    @AfterEach
    void setup() {
        matchRepository.deleteAll();
        representationRepository.deleteAll();
        clubRepository.deleteAll();
    }

    @Test
    @DisplayName("Should get all matches")
    void shouldGetAllMatches() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        List<ClubRepresentation> representationList = TestDataFactory.getRepresentationList1(clubList);
        matchRepository.saveAll(TestDataFactory.getMatchList(representationList));

        //when + then
        this.mockMvc.perform(get("/matches/"))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$", hasSize(4)));
    }

    @Test
    void shouldGetMatchById() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        List<ClubRepresentation> representationList = TestDataFactory.getRepresentationList1(clubList);
        List<Match> matchList = matchRepository.saveAll(TestDataFactory.getMatchList(representationList));

        int matchId = matchList.get(1).getId();

        //when + then
        this.mockMvc.perform(get("/matches/" + matchId))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$").isMap(),
                        jsonPath("$.nameOfReferee", is("Referee2")));
    }

    @Test
    void shouldFailToGetMatchById() throws Exception {
        //when + then
        this.mockMvc.perform(get("/matches/10000"))
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    void shouldFindRefereeByName() throws Exception {
        //Creating example matches
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        List<ClubRepresentation> representationList = TestDataFactory.getRepresentationList1(clubList);
        List<Match> matchList = matchRepository.saveAll(TestDataFactory.getMatchList(representationList));

        int matchId = matchList.get(0).getId();

        this.mockMvc.perform(get("/matches/byRefereeName/?name=Referee1"))
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$[0].id", is(matchId)));
    }
}
