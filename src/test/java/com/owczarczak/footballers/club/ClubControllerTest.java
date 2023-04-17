package com.owczarczak.footballers.club;

import com.owczarczak.footballers.TestDataFactory;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

import static java.time.LocalDateTime.of;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ClubControllerTest {
    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubRepresentationRepository repRepository;

    @Autowired
    private MockMvc mockMvc;

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
    void shouldGetEmptyAllClubs() throws Exception {
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
    @DisplayName("Should not get club by id")
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
                        jsonPath("$", hasSize(2)));
    }

    //fixme
    @Test
    void shouldDeleteByCLubId() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        int initialSize = clubList.size();

        int clubId = clubList.get(0).getId();

        //when + then
        this.mockMvc.perform(delete("/clubs/" + clubId))
                .andDo(print())
                .andExpect(status().isOk());

        assertEquals(initialSize - 1, clubRepository.findAll().size());
        assertNotEquals("Barcelona", clubRepository.findAll().get(0).getName());

        List<Club> clubs = clubRepository.findAll();

        boolean containsClubName = clubs.stream().anyMatch(i -> i.equals("Barcelona"));
        assertFalse(containsClubName);
    }

    //fixme
    @Test
    @DisplayName("Should add a club")
    void shouldAddClub() throws Exception {
        //given
        LocalDateTime localDateTime1 = of(2000, 1, 5, 1, 10, 0);
        Club club = new Club("Manchester", localDateTime1.toInstant(ZoneOffset.UTC), Collections.emptyList());

        String request = """
                "name":"TestClub1",
                "created":"2015-12-30T19:34:50.63Z"
                """;

        //when + then
        this.mockMvc.perform(post("/clubs/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(
//                        status().isCreated(),
                        jsonPath("$").isMap());

        Assertions.assertEquals(1, clubRepository.findAll().size());
    }

    @Test
    void shouldNotDeleteClubById() throws Exception {
        //when + then
        this.mockMvc.perform(delete("/clubs/0"))
                .andDo(print())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
