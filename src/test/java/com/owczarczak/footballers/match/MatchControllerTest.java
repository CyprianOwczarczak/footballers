package com.owczarczak.footballers.match;

import com.owczarczak.footballers.TestDataFactory;
import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MatchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ClubRepresentationRepository representationRepository;

    @Autowired
    private ClubRepository clubRepository;

//    @AfterEach
//    void setup() {
//        matchRepository.deleteAll();
//        representationRepository.deleteAll();
//        clubRepository.deleteAll();
//    }

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
    @DisplayName("Should get match by id")
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
    @DisplayName("Should not get match by id")
    void shouldNotToGetMatchById() throws Exception {
        //when + then
        this.mockMvc.perform(get("/matches/10000"))
                .andExpect(jsonPath("$").doesNotExist());
    }

    @Test
    @DisplayName("Should find referee by name")
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


    @Test
    @DisplayName("Should delete match by id")
    void shouldDeleteMatchById() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        List<ClubRepresentation> representationList = TestDataFactory.getRepresentationList1(clubList);
        List<Match> matchList = matchRepository.saveAll(TestDataFactory.getMatchList(representationList));

        int matchId = matchList.get(0).getId();

        //when + then
        this.mockMvc.perform(delete("/matches/" + matchId))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(3, matchRepository.findAll().size());

        //It deletes the representations saved by cascade
        assertEquals(6, representationRepository.findAll().size());
    }

    @Test
    @DisplayName("Should not delete match by id")
    void shouldNotDeleteMatchById() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        List<ClubRepresentation> representationList = TestDataFactory.getRepresentationList1(clubList);
        List<Match> matchList = matchRepository.saveAll(TestDataFactory.getMatchList(representationList));

        int matchId = matchList.get(0).getId() + 1000;

        //when + then
        this.mockMvc.perform(delete("/matches/" + matchId))
                .andDo(print());

        assertEquals(4, matchRepository.findAll().size());
        assertEquals(8, representationRepository.findAll().size());
    }

    //TODO add POST test
    @Test
    @DisplayName("Should add match")
    void shouldAddMatch() throws Exception {
        //given
//        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
//        List<ClubRepresentation> representationList = TestDataFactory.getRepresentationList1(clubList);

        String request = """
                {
                	"pesel": "222222",
                               
                	"host":{
                        "club":{
                            "name":"ClubNamePost1",
                            "created":"2015-12-30T19:34:50.63Z"
                        }
                    },
                               
                    "guest":{
                        "club":{
                            "name":"ClubNamePost2",
                            "created":"2015-12-30T19:34:50.63Z"
                        }
                    },
                               
                    "nameOfReferee": "RefereePost1",
                    "date": "2018-12-30T19:34:50.63Z"
                    "scores":{
                               
                    }
                }
                """;
        //when + then
        this.mockMvc.perform(post("/")
                .content(request))
                .andExpect(jsonPath("$").isMap());
    }
}
