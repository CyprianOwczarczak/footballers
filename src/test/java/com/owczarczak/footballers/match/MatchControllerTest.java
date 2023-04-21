package com.owczarczak.footballers.match;

import com.owczarczak.footballers.IntegrationTestBasedClass;
import com.owczarczak.footballers.TestDataFactory;
import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.footballer.FootballerRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
public class MatchControllerTest extends IntegrationTestBasedClass {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private ClubRepresentationRepository representationRepository;

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    FootballerRepository footballerRepository;

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

        Long matchId = matchList.get(1).getId();

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

        Integer matchId = Math.toIntExact(matchList.get(0).getId());

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

        Long matchId = matchList.get(0).getId();

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

        long matchId = matchList.get(0).getId() + 1000;

        //when + then
        this.mockMvc.perform(delete("/matches/" + matchId))
                .andDo(print());

        assertEquals(4, matchRepository.findAll().size());
        assertEquals(8, representationRepository.findAll().size());
    }

    @Test
    @DisplayName("Should add match")
    void shouldAddMatch() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        List<Footballer> footballerList = footballerRepository.saveAll(TestDataFactory.getFootballerList());

        Long clubId1 = clubList.get(0).getId();
        Long clubId2 = clubList.get(1).getId();

        Long footballerId1 = footballerList.get(0).getId();
        Long footballerId2 = footballerList.get(1).getId();
        Long footballerId3 = footballerList.get(2).getId();
        Long footballerId4 = footballerList.get(3).getId();

        String request = """
                {
                "guestRepresentation":{
                "clubId":%d,
                "footballersIdList": [%d,%d]
                },
                                
                "hostRepresentation":{
                "clubId":%d,
                "footballersIdList": [%d,%d]
                },
                                
                "nameOfReferee":"PostReferee",
                "date":"2000-01-01T17:09:42.411"
                }
                """.formatted(clubId1, footballerId1, footballerId2, clubId2, footballerId3, footballerId4);

        //when + then
        this.mockMvc.perform(post("/matches/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpectAll(
                        jsonPath("$").isMap(),
                        jsonPath("$.nameOfReferee", is("PostReferee")),
                        jsonPath("$.date", is("2000-01-01T17:09:42.411")),
                        status().isCreated());
        assertEquals(1, matchRepository.findAll().size());
    }
}
