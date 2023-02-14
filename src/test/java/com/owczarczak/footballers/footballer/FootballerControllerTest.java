package com.owczarczak.footballers.footballer;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@AutoConfigureTestDatabase
class FootballerControllerTest {

    // Should update a footballer --> return 2xx successful, footballer exists with new data
    // Should fail to update a footballer --> return 4xx not found, footballer with that id doesn't exist


    // Should delete a footballer --> return 2xx, check if footballer with that id doesn't exist anymore
    // Should fail to delete a footballer --> footballer with that id doens't exist already,
    // check the size of the footballerList before and after deletion

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
        this.mockMvc.perform(get("/footballers/6"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
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
        Footballer footballer = new Footballer(1234, "123456", "testPlayer", "testClub", 20, 180);
        repository.save(footballer);
        //Get footballersList and check the size
        this.mockMvc.perform(get("/footballers/byName/?name=testPlayer"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    //Should get multiple footballers by name, check the size of the list and parameters with jsonPath
    @Test
    @DisplayName("Should get multiple footballers by name")
    void shouldGetMultipleFootballersByName() throws Exception {
        Footballer footballer1 = new Footballer("123456", "testPlayer", "testClub", 20, 180);
        Footballer footballer2 = new Footballer("234567", "testPlayer", "testClub", 20, 180);
        Footballer footballer3 = new Footballer("345678", "testPlayer", "testClub", 20, 180);
        repository.save(footballer1);
        repository.save(footballer2);
        repository.save(footballer3);
        this.mockMvc.perform(get("/footballers/byName/?name=testPlayer"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

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


    // Should not get any footballers by name --> check if the list is empty
//    @Test
//    @DisplayName("Should not get any footballers by name")
//    void shouldNotGetAnyFootballersByName() throws Exception {
//        //Get footballersList and check is the size is 0
//        this.mockMvc.perform(get("/footballers/byName/?name=xyz"))
//                .andDo(print())
//                .andExpect(status().is4xxClientError());
//    }

//     Should add a footballer --> return 2xx created, check if the player was added and if he has the parameters we wanted
    @Test
    @DisplayName("Should add a footballer")
    void shouldAddFootballer() throws Exception {
        Footballer footballer = new Footballer(53527, "4125", "testPlayer", "testClub", 20, 180);
        int returnedFootballerId = repository.save(footballer).getId();
        this.mockMvc.perform(get("/footballers/" + returnedFootballerId))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    // Should fail to add a footballer -->  footballer not added, wrong body passed or the id already exists
    @Test
    @DisplayName("Should fail to add a footballer")
    void shouldFailToAddFootballer() throws Exception {
        Footballer footballer = new Footballer("12345678", "testPlayer", "testClub", 20, 180);
        int returnedFootballerId = repository.save(footballer).getId();
        this.mockMvc.perform(get("/footballers/" + returnedFootballerId))
                .andDo(print())
                .andExpect(status().is4xxClientError());
    }

    // Should Get X Highest Footballers --> check if the list size is X
    @Test
    @DisplayName("Should get X highest footballers")
    void shouldGetXHighestFootballers() throws Exception {
    }

    // Shouldn't get more than X footballers --> check if it doesn't return more footballers
    // than there are on a list/ doesn't duplicate
    @Test
    @DisplayName("Should not get more than X footballers")
    void shouldNotGetMoreThanXFootballers() {

    }

}