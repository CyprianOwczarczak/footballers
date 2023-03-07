package com.owczarczak.footballers.score;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.footballer.FootballerRepository;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ScoreControllerTest {

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    FootballerRepository footballerRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ClubRepresentationRepository repRepository;

    @BeforeEach
    void setup() {
        scoreRepository.deleteAll();
        footballerRepository.deleteAll();
    }

    @Test
    void shouldGetAvgGoalsPerMatchForFootballer() throws Exception {
        this.mockMvc.perform(get("/scores/getAverageGoals"))
                .andDo(print());
    }

    
    // Create example Footballers
    private void createExampleFootballers() {
        Footballer footballer1 = new Footballer("111111", "Lewandowski", 150);
        Footballer footballer2 = new Footballer("222222", "Neymar", 160);
        Footballer footballer3 = new Footballer("333333", "Ikar", 160);
        Footballer footballer4 = new Footballer("444444", "Messi", 170);
        Footballer footballer5 = new Footballer("555555", "Ronaldo", 170);
        Footballer footballer6 = new Footballer("666666", "Zlatan", 180);

        for (Footballer footballer : Arrays.asList(
                footballer1, footballer2, footballer3, footballer4, footballer5, footballer6)) {
            footballerRepository.save(footballer);
        }
    }

    // Create example Scores




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

    //Create example ClubRepresentations
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
