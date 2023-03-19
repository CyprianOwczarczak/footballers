package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.TestDataFactory;
import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClubRepresentationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ClubRepresentationRepository representationRepository;

    @Autowired
    ClubRepository clubRepository;

    @AfterEach
    void setup() {
        representationRepository.deleteAll();
        clubRepository.deleteAll();
    }

    @Test
    @DisplayName("Should get all club representations")
    void shouldGetAllClubRepresentations() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        representationRepository.saveAll(TestDataFactory.getRepresentationList1(clubList));

        //when + then
        this.mockMvc.perform(get("/representation/"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(12)))
                .andDo(print());
    }

    @Test
    @DisplayName("Should not get club representattions from empty list")
    void shouldNotGetClubRepresentationsFromEmptyList() throws Exception {
        //when + then
        this.mockMvc.perform(get("/representation/"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(0)))
                .andDo(print());
    }

    @Test
    @DisplayName("Should get club representation by id")
    void shoudGetClubRepresentationById() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        List<ClubRepresentation> representationList =
                representationRepository.saveAll(TestDataFactory.getRepresentationList1(clubList));
        int clubRepresentationId = representationList.get(0).getId();

        //when + then
        this.mockMvc.perform(get("/representation/" + clubRepresentationId))
                .andDo(print())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.clubName", is("Barcelona")));
    }

    @Test
    @DisplayName("Should not get club representation by id")
    void shoudNotGetClubRepresentationById() throws Exception {
        //when + then
        this.mockMvc.perform(get("/representation/" + 1))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should delete club representation by id")
    void shouldDeleteClubRepresentationById() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        List<ClubRepresentation> representationList =
                representationRepository.saveAll(TestDataFactory.getRepresentationList1(clubList));
        int clubRepresentationId = representationList.get(2).getId();

        //when + then
        this.mockMvc.perform(delete("/representation/" + clubRepresentationId))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        Assertions.assertEquals(11, representationRepository.findAll().size());
    }

    @Test
    @DisplayName("Should delete club representation by id")
    void shouldNotDeleteClubRepresentationById() throws Exception {
        //when + then
        this.mockMvc.perform(delete("/representation/" + 1))
                .andDo(print())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
