package com.owczarczak.footballers.footballer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.isEmptyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
//@AutoConfigureTestDatabase
class FootballerControllerTest {

    // Should update a footballer --> return 2xx successful, footballer exists with new data
    // Should fail to update a footballer --> return 4xx not found, footballer with that id doesn't exist


    // Should delete a footballer --> return 2xx, check if footballer with that id doesn't exist anymore
    // Should fail to delete a footballer --> footballer with that id doens't exist already,
    // check the size of the footballerList before and after deletion

    //TODO
//    mockMvc.perform(get("/api/deliverypoints/?customerIds=" + ids))
//            .andDo(print())
//            .andExpectAll(
//            content().contentType(MediaType.APPLICATION_JSON),
//    status().is2xxSuccessful(),
//    jsonPath("$").isArray(),
//    jsonPath("$", hasSize(3)),
//    jsonPath("$[0].address.street", is("Św. Antoniego")),
//    jsonPath("$[0].name", is("Fabryka w Poznaniu")),
//    jsonPath("$[1].address.city", is("Wrocław")),
//    jsonPath("$[1].name", is("Fabryka w mieście krasnali")));

    @Autowired
    FootballerRepository repository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    @Test
    @DisplayName("Should startup Spring")
    void shouldStartupSpring() {
    }

    // Should get all footballers --> return 2xx successful, check list size
    @Test
    @DisplayName("Should get all footballers")
    void shouldGetAllFootballers() throws Exception {
        this.mockMvc.perform(get("/footballers/"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    // Should get a footballer by id --> return 2xx successful code, only one object
    @Test
    @DisplayName("Should get a footballer by id")
    void shouldGetFootballerById() throws Exception {
        Footballer footballer1 = new Footballer("111111", "testPlayer", "testClub", 10, 150);
        repository.save(footballer1);
        this.mockMvc.perform(get("/footballers/1"))
                .andDo(print())
                .andExpectAll(status().is2xxSuccessful());
    }

    // Should fail to get footballer by id -->return 4xx not found
    @Test
    @DisplayName("Should fail to get footballer by id")
    void shouldNotGetFootballerById() throws Exception {
        this.mockMvc.perform(get("/footballers/5432"))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    // Should get footballers by name --> check if the list size is correct
    @Test
    @DisplayName("Should get footballers by name")
    void shouldGetFootballersByName() throws Exception {
        Footballer footballer1 = new Footballer("111111", "testPlayer", "testClub", 10, 150);
        repository.save(footballer1);
        //Get footballersList and check the size
        this.mockMvc.perform(get("/footballers/byName/?name=testPlayer"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    //Should get multiple footballers by name, check the size of the list and parameters with jsonPath
    @Test
    @DisplayName("Should get multiple footballers by name")
    void shouldGetMultipleFootballersByName() throws Exception {
        Footballer footballer1 = new Footballer("111111", "testPlayer", "testClub", 10, 150);
        Footballer footballer2 = new Footballer("222222", "testPlayer", "testClub2", 20, 160);
        Footballer footballer3 = new Footballer("333333", "testPlayer2", "testClub3", 30, 170);
        repository.save(footballer1);
        repository.save(footballer2);
        repository.save(footballer3);
        this.mockMvc.perform(get("/footballers/byName/?name=testPlayer"))
                .andDo(print())
                .andExpectAll(status().is2xxSuccessful(),
                        jsonPath("$", hasSize(2)));
    }


    //Should not get any footballers by name --> check if the list is empty
    @Test
    @DisplayName("Should not get any footballers by name")
    void shouldNotGetAnyFootballersByName() throws Exception {
        //Get footballersList and check is the size is 0
        this.mockMvc.perform(get("/footballers/byName/?name=xyz"))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$", hasSize(0))
                );
    }

    //     Should add a footballer --> return 2xx created, check if the player was added and if he has the parameters we wanted
    @Test
    @DisplayName("Should add a footballer")
    void shouldAddFootballer() throws Exception {
        Footballer footballer = new Footballer("111111", "testPlayer", "testClub", 20, 180);
        int returnedFootballerId = repository.save(footballer).getId();
        this.mockMvc.perform(get("/footballers/" + returnedFootballerId))
                .andDo(print())
                .andExpectAll(status().isCreated(),
                        jsonPath("$", hasSize(1)));
    }

    // Should fail to add a footballer -->  footballer not added, wrong body passed or the id already exists
    @Test
    @DisplayName("Should fail to add a footballer")
    void shouldFailToAddFootballer() throws Exception {
        Footballer footballer = new Footballer("12345678", "testPlayer", "testClub", 20, 180);
        int returnedFootballerId = repository.save(footballer).getId();
        this.mockMvc.perform(get("/footballers/" + returnedFootballerId))
                .andDo(print())
                .andExpect(jsonPath("$", isEmptyString()));
    }

    // Should Get X Highest Footballers --> check if the list size is X
    @Test
    @DisplayName("Should get X highest footballers")
    void shouldGetXHighestFootballers() throws Exception {
        Footballer footballer1 = new Footballer("111111", "testPlayer", "testClub", 10, 150);
        Footballer footballer2 = new Footballer("222222", "testPlayer", "testClub2", 20, 160);
        Footballer footballer3 = new Footballer("333333", "testPlayer", "testClub3", 30, 170);
        repository.save(footballer1);
        repository.save(footballer2);
        repository.save(footballer3);
        this.mockMvc.perform(get("/footballers/topXByHeight/?pageNumber=0&numberOfPlayers=2"))
                .andDo(print())
                .andExpectAll(jsonPath("$", hasSize(2)));
        //todo add check for the highest and second highest footballer
    }

    // Shouldn't get more than X footballers --> check if it doesn't return more footballers
    // than there are on a list/ doesn't duplicate
    @Test
    @DisplayName("Should not get more than X footballers")
    void shouldNotGetMoreThanXFootballers() {

    }

}