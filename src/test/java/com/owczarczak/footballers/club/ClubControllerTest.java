package com.owczarczak.footballers.club;

import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.Arrays;
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

    @BeforeEach
    void setup() {
        repRepository.deleteAll();
        clubRepository.deleteAll();
    }

    //Fixme
    @Test
    void shouldGetAllClubsWhichPlayedMoreThan3Matches() throws Exception {
        createExampleClubs();
        createExampleClubRepresentations();

        this.mockMvc.perform(get("/clubs/MoreThan3Matches"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    //Create example clubs (without footballersList)
    private void createExampleClubs() {
        Club club1 = new Club("Barcelona", Instant.now());
        Club club2 = new Club("Lech", Instant.now());
        Club club3 = new Club("Real", Instant.now());
        Club club4 = new Club("Manchester", Instant.now());
        Club club5 = new Club("Bayern", Instant.now());

        clubRepository.save(club1);
        clubRepository.save(club2);
        clubRepository.save(club3);
        clubRepository.save(club4);
        clubRepository.save(club5);
    }

    private void createExampleClubRepresentations() {
        List<Club> clubList = clubRepository.findAll();

        ClubRepresentation representation1 = new ClubRepresentation(clubList.get(0));
        ClubRepresentation representation2 = new ClubRepresentation(clubList.get(0));
        ClubRepresentation representation3 = new ClubRepresentation(clubList.get(0));
        ClubRepresentation representation4 = new ClubRepresentation(clubList.get(0));
        ClubRepresentation representation5 = new ClubRepresentation(clubList.get(1));
        ClubRepresentation representation6 = new ClubRepresentation(clubList.get(1));
        ClubRepresentation representation7 = new ClubRepresentation(clubList.get(2));
        ClubRepresentation representation8 = new ClubRepresentation(clubList.get(2));
        ClubRepresentation representation9 = new ClubRepresentation(clubList.get(2));
        ClubRepresentation representation10 = new ClubRepresentation(clubList.get(2));
        ClubRepresentation representation11 = new ClubRepresentation(clubList.get(3));
        ClubRepresentation representation12 = new ClubRepresentation(clubList.get(3));

        for (ClubRepresentation clubRepresentation : Arrays.asList(
                representation1, representation2, representation3, representation4, representation5, representation6,
                representation7, representation8, representation9, representation10, representation11, representation12)) {
            repRepository.save(clubRepresentation);
        }
    }
}
