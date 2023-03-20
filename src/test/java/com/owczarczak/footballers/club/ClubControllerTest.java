package com.owczarczak.footballers.club;

import com.owczarczak.footballers.TestDataFactory;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import org.junit.jupiter.api.AfterEach;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        repRepository.deleteAll();
        clubRepository.deleteAll();
    }

    @Test
    @DisplayName("Should get all clubs")
    void shouldGetAllClubs() throws Exception {
        //given
        clubRepository.saveAll(TestDataFactory.getClubList());

        //when + then
        this.mockMvc.perform(get("/clubs/"))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$", hasSize(5)));
    }

    @Test
    @DisplayName("Should not get all clubs")
    void shouldNotGetAllClubs() throws Exception {
        //when + then
        this.mockMvc.perform(get("/clubs/"))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$", hasSize(0)));
    }

    @Test
    @DisplayName("Should get club by id")
    void shouldGetClubById() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        int clubId = clubList.get(0).getId();

        //when + then
        this.mockMvc.perform(get("/clubs/" + clubId))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$").isMap(),
                        jsonPath("$.name", is("Barcelona")));
    }

    @Test
    @DisplayName("Should get club by id")
    void shouldNotGetClubById() throws Exception {
        //when + then
        this.mockMvc.perform(get("/clubs/" + 1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should get all clubs which played more than 3 matches")
    void shouldGetAllClubsWhichPlayedMoreThan3Matches() throws Exception {
        //given --> saving ClubRepresentations with Club arguments (without footballerList)
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        repRepository.saveAll(TestDataFactory.getRepresentationList1(clubList));

        //when + then
        this.mockMvc.perform(get("/clubs/MoreThan3Matches"))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$", hasSize(2)),
                        jsonPath("$[0].name", is("Barcelona")),
                        jsonPath("$[1].name", is("Real")));
    }
}
