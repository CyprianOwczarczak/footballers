package com.owczarczak.footballers.contract;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.footballer.FootballerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.owczarczak.footballers.TestDataFactory.getClubList;
import static com.owczarczak.footballers.TestDataFactory.getContractList1;
import static com.owczarczak.footballers.TestDataFactory.getFootballerList;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    @DisplayName("Should get all contracts")
    void shouldGetAllContracts() throws Exception {
        //given
        clubRepository.saveAll(getClubList());
        footballerRepository.saveAll(getFootballerList());
        contractRepository.saveAll(getContractList1(clubRepository.findAll(), footballerRepository.findAll()));
        //when
        this.mockMvc.perform(get("/contracts/"))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$", hasSize(3)));
    }

    @Test
    @DisplayName("Should get contract by id")
    void shouldGetContractById() throws Exception {
        //given
        clubRepository.saveAll(getClubList());
        footballerRepository.saveAll(getFootballerList());
        List<Contract> contractList =
                contractRepository.saveAll(getContractList1(clubRepository.findAll(), footballerRepository.findAll()));

        int contractId = contractList.get(0).getId();

        //when + then
        this.mockMvc.perform(get("/contracts/" + contractId))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$").isMap(),
                        jsonPath("$.salary", is(10000)));
    }

    @Test
    @DisplayName("Should not get contract by id")
    void shouldNotGetContractById() throws Exception {
        //when + then
        this.mockMvc.perform(get("/contracts/" + 0))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should get list of contracts for specific footballer")
    void shouldGetListOfContractsForSpecificFootballer() throws Exception {
        // given --> creating example data to create the Contracts
        List<Club> clubList = clubRepository.saveAll(getClubList());
        List<Footballer> footballerList = footballerRepository.saveAll(getFootballerList());
        contractRepository.saveAll(getContractList1(clubList, footballerList));

        //Getting the id of the first footballer from the list to check contracts for him
        int searchedId = footballerList.get(0).getId();
        // when + then
        this.mockMvc.perform(get("/contracts/contractsForFootballer/" + searchedId))
                .andDo(print())
                .andExpectAll(jsonPath("$").isArray(),
                        jsonPath("$[0].clubName", is("Barcelona")),
                        jsonPath("$[0].footballerName", is("Lewandowski")),
                        jsonPath("$[0].salary", is(10000)));
    }

    @Test
    @DisplayName("Should get mean length of contracts in a specific club")
    void shouldGetMeanLenghtOfContractsInSpecificClub() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(getClubList());
        footballerRepository.saveAll(getFootballerList());
        contractRepository.saveAll(getContractList1(clubRepository.findAll(), footballerRepository.findAll()));
        int clubId = clubList.get(0).getId();

        //when + then
        this.mockMvc.perform(get("/contracts/lengthOf/" + clubId))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$").isMap(),
                        jsonPath("$.averageLength", is(1096)));
    }

    @Test
    @DisplayName("Should delete contract by id")
    void shouldDeleteContractById() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(getClubList());
        footballerRepository.saveAll(getFootballerList());
        List<Contract> contractList =
                contractRepository.saveAll(getContractList1(clubRepository.findAll(), footballerRepository.findAll()));

        int clubId = contractList.get(0).getId();

        //when + then
        this.mockMvc.perform(delete("/contracts/" + clubId))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
        Assertions.assertEquals(2, contractRepository.findAll().size());
    }

    @Test
    @DisplayName("Should not delete contract by id")
    void shouldNotDeleteContractById() throws Exception {
        //when + then
        this.mockMvc.perform(delete("/contracts/" + 0))
                .andDo(print());
    }

    @Test
    void shouldUpdateContractLength() throws Exception {
        //given
        List<Club> clubList = clubRepository.saveAll(getClubList());
        List<Footballer> footballerList = footballerRepository.saveAll(getFootballerList());
        List<Contract> contractList =
                contractRepository.saveAll(getContractList1(clubList, footballerList));

        int idToUpdate = contractList.get(0).getId();

        //when + then
        this.mockMvc.perform(put("/contracts/" + idToUpdate + "?daysToAdd=12"))
                .andDo(print())
                .andExpectAll(status().isOk(),
                        jsonPath("$").isMap(),
                        jsonPath("$.clubName", is("Barcelona")),
                        jsonPath("$.contractEnd", is("2018-01-17T01:10:00Z"))
                );
    }
}
