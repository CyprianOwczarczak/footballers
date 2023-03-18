package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.TestDataFactory;
import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ClubRepresentationTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ClubRepresentationRepository representationRepository;

    @Autowired
    ClubRepository clubRepository;

    @BeforeEach
    void setup() {
        representationRepository.deleteAll();
        clubRepository.deleteAll();
    }

    @AfterEach
    void setup2() {
        representationRepository.deleteAll();
        clubRepository.deleteAll();
    }

    @Test
    void shouldGetAllClubRepresentations() throws Exception {
        //Adding test data
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList());
        representationRepository.saveAll(TestDataFactory.getRepresentationList1(clubList));

        this.mockMvc.perform(get("/representation/"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(12)))
                .andDo(print());
    }
}
