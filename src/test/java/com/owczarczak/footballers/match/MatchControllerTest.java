package com.owczarczak.footballers.match;

import com.owczarczak.footballers.TestDataFactory;
import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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

    @AfterEach
    void setup() {
        matchRepository.deleteAll();
        representationRepository.deleteAll();
        clubRepository.deleteAll();
    }

    //FIXME no value at JSON path "$"
    @Test
    void shouldFindRefereeByName() throws Exception {
        //Creating example matches
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        List<ClubRepresentation> representationList = TestDataFactory.getRepresentationList1(clubList);
        matchRepository.saveAll(TestDataFactory.getMatchList(representationList));

        this.mockMvc.perform(get("/matches/byRefereeName/?name=Referee1"))
                .andExpect(jsonPath("$").isMap())
                .andDo(print());
    }
}
