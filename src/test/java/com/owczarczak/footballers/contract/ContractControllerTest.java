package com.owczarczak.footballers.contract;

import com.owczarczak.footballers.contract.ContractRepository;
import com.owczarczak.footballers.footballer.FootballerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ContractControllerTest {

    @Autowired
    ContractRepository repository;

    @Autowired
    MockMvc mockMvc;

//    @BeforeEach ?

    //TODO add JsonPatch assertions
    @Test
    void shouldGetAllContracts() throws Exception {
        this.mockMvc.perform(get("/contracts/"))
                .andDo(print());
    }

    @Test
    void shouldGetListOfContractsForSpecificFootballer() throws Exception {
        this.mockMvc.perform(get("/contracts/" + 1));
    }

    @Test
    void shouldGetMeanLenghtOfContractsInSpecificClub() throws Exception {
        this.mockMvc.perform(get("/contracts/lengthOfContracts" + 1))
                .andDo(print());
    }
}

