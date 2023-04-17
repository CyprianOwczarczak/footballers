package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.TestDataFactory;
import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClubRepresentationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClubRepresentationRepository representationRepository;

    @Autowired
    private ClubRepository clubRepository;

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
                .andDo(print())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$", hasSize(12)));
    }

    @Test
    @DisplayName("Should not get club representattions from empty list")
    void shouldNotGetClubRepresentationsFromEmptyList() throws Exception {
        //when + then
        this.mockMvc.perform(get("/representation/"))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$", hasSize(0)));
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
                .andExpectAll(
                        jsonPath("$").isMap(),
                        jsonPath("$.clubName", is("Barcelona")));
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
    @DisplayName("Should not delete club representation by id")
    void shouldNotDeleteClubRepresentationById() throws Exception {
        //when + then
        this.mockMvc.perform(delete("/representation/" + 1))
                .andDo(print())
                .andExpect(jsonPath("$").doesNotExist());
    }

//    @Test
//    @DisplayName("Should add a footballer")
//    void shouldAddFootballer() throws Exception {
//        String request = """
//                {
//                "pesel":"333333",
//                "name":"testPlayer3",
//                "height":170
//                }
//                """;
//
//        this.mockMvc.perform(post("/footballers/")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(request))
//                .andDo(print())
//                .andExpectAll(status().isCreated(),
//                        jsonPath("$").isMap())
//                .andExpectAll(getJsonValidationRules());
//    }

    @Test
    void shouldAddRepresentation() throws Exception {
        String request = """
                "club":{
                
                }
                "footballerList":
                
                """;

//        .id(representationToBeAdded.getId())
//                .club(representationToBeAdded.getClub())
//                .footballerList

//        {
//            "pesel":"333333",
//                "name":"testPlayer3",
//                "height":170
//        }

        this.mockMvc.perform(post("/representation/"))
                .andDo(print())
                .andExpectAll(status().isCreated(),
                        jsonPath("$").isMap());
    }
}
