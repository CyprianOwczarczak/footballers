package com.owczarczak.footballers.contract;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.footballer.FootballerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.owczarczak.footballers.TestDataFactory.*;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContractControllerTest {

    @Autowired
    ContractRepository contractRepository;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    FootballerRepository footballerRepository;

    @Autowired
    MockMvc mockMvc;

    @AfterEach
    void setup() {
        contractRepository.deleteAll();
        clubRepository.deleteAll();
        footballerRepository.deleteAll();
    }

    @Test
    void shouldGetAllContracts() throws Exception {
        //given
        clubRepository.saveAll(getClubList());
        footballerRepository.saveAll(getFootballerList());
        contractRepository.saveAll(getContractList1(clubRepository.findAll(), footballerRepository.findAll()));
        //when
        this.mockMvc.perform(get("/contracts/"))
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @Test
    void shouldGetListOfContractsForSpecificFootballer() throws Exception {
        // given --> creating example data to create the Contracts
        clubRepository.saveAll(getClubList());
        List<Footballer> list = footballerRepository.saveAll(getFootballerList());
        contractRepository.saveAll(getContractList1(clubRepository.findAll(), footballerRepository.findAll()));

        //Getting the id of the first footballer from the list to check contracts for him
        int searchedId = list.get(0).getId();

        // when + then
        this.mockMvc.perform(get("/contracts/" + searchedId))
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].clubName", is("Barcelona")))
                .andExpect(jsonPath("$[0].footballerName", is("Lewandowski")))
                .andExpect(jsonPath("$[0].salary", is(10000)));
    }

    @Test
    void shouldGetMeanLenghtOfContractsInSpecificClub() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(getClubList());
        footballerRepository.saveAll(getFootballerList());
        contractRepository.saveAll(getContractList1(clubRepository.findAll(), footballerRepository.findAll()));
        int clubId = clubList.get(0).getId();
        System.out.println("THE CLUB ID IS: " + clubId);

        //when + then
        this.mockMvc.perform(get("/contracts/lengthOf/" + clubId))
                .andDo(print())
                .andExpect(jsonPath("$").isMap())
                .andExpect(jsonPath("$.averageLength", is(100)));
    }
}
