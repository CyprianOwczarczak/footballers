package com.owczarczak.footballers.club;

import com.owczarczak.footballers.TestDataFactory;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ClubControllerTest {
    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ClubRepresentationRepository repRepository;

    @Autowired
    MockMvc mockMvc;

    @AfterEach
    void setup() {
        clubRepository.deleteAll();
        repRepository.deleteAll();
    }

    //Fixme all clubs with assigned Representations are returned
    @Test
    void shouldGetAllClubsWhichPlayedMoreThan3Matches() throws Exception {
//        List<Club> clubList = createExampleClubs();
//        List<ClubRepresentation> clubRepresentations = createExampleClubRepresentations();

        //given --> saving ClubRepresentations with Club arguments (without footballerList)
        clubRepository.saveAll(TestDataFactory.getClubList1());
        repRepository.saveAll(TestDataFactory.getRepresentationList1()); //fixme

        //when + then
        this.mockMvc.perform(get("/clubs/MoreThan3Matches"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));
    }
}
