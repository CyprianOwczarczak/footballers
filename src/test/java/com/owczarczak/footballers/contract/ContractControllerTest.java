package com.owczarczak.footballers.contract;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.footballer.FootballerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ContractControllerTest {

    //Fixme --> (update or delete on table "club" violates foreign key constraint "fk_club" on table "club_representation")

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    FootballerRepository footballerRepository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        contractRepository.deleteAll();
        clubRepository.deleteAll();
        footballerRepository.deleteAll();
    }

    @Test
    void shouldGetListOfContractsForSpecificFootballer() throws Exception {
        createExampleClubs();
        createExampleFootballers();
        createExampleContracts();

        this.mockMvc.perform(get("/contracts/" + 1))
                .andDo(print());
    }

    @Test
    void shouldGetMeanLenghtOfContractsInSpecificClub() throws Exception {
        createExampleClubs();
        createExampleFootballers();
        createExampleContracts();

        this.mockMvc.perform(get("/contracts/lengthOfContracts" + 1))
                .andDo(print());
    }

    //Create example Clubs (to add in Contracts)
    private void createExampleClubs() {
        Club club1 = new Club("Barcelona", Instant.now());
        Club club2 = new Club("Lech", Instant.now());
        Club club3 = new Club("Real", Instant.now());
        Club club4 = new Club("Manchester", Instant.now());
        Club club5 = new Club("Bayern", Instant.now());

        for (Club club : Arrays.asList(
                club1, club2, club3, club4, club5)) {
            clubRepository.save(club);
        }
    }

    //Create example Footballers (to add in Contracts)
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

    //Create example Contracts
    private void createExampleContracts() {
        List<Club> clubList = clubRepository.findAll();
        List<Footballer> footballerList = footballerRepository.findAll();

        Contract contract1 = new Contract(clubList.get(0), footballerList.get(0), Instant.now().minus(1, ChronoUnit.YEARS), Instant.now(), 10000);
        Contract contract2 = new Contract(clubList.get(1), footballerList.get(1), Instant.now().minus(2, ChronoUnit.YEARS), Instant.now(), 10000);
        Contract contract3 = new Contract(clubList.get(2), footballerList.get(2), Instant.now().minus(3, ChronoUnit.YEARS), Instant.now(), 10000);

        contractRepository.save(contract1);
        contractRepository.save(contract2);
        contractRepository.save(contract3);
    }
}

