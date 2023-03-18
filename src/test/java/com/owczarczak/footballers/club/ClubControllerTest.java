package com.owczarczak.footballers.club;

import com.owczarczak.footballers.TestDataFactory;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
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
public class ClubControllerTest {
    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ClubRepresentationRepository repRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup2() {
        repRepository.deleteAll();
        clubRepository.deleteAll();
    }

    @AfterEach
    void setup() {
        repRepository.deleteAll();
        clubRepository.deleteAll();
    }

    @Test
    void shouldGetAllClubs() throws Exception {
        //given
        clubRepository.saveAll(TestDataFactory.getClubList());

        //when + then
        this.mockMvc.perform(get("/clubs/"))
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(5)))

        ;
    }

    @Test
    void shouldGetAllClubsWhichPlayedMoreThan3Matches() throws Exception {
        //given --> saving ClubRepresentations with Club arguments (without footballerList)
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        repRepository.saveAll(TestDataFactory.getRepresentationList1(clubList));

        //when + then
        this.mockMvc.perform(get("/clubs/MoreThan3Matches"))
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Barcelona")))
                .andExpect(jsonPath("$[1].name", is("Real")));
    }
}
