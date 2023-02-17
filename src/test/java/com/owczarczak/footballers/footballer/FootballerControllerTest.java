package com.owczarczak.footballers.footballer;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FootballerControllerTest {

    @Autowired
    FootballerRepository repository;

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        repository.deleteAll();
    }

    //TODO Przerobić JsonPath na metodę z tablicą obiektów

    private static Footballer getFootballer1() {
        return new Footballer("111111", "testPlayer1", "testClub", 10, 150);
    }

    private static Footballer getFootballer2() {
        return new Footballer("222222", "testPlayer2", "testClub2", 20, 160);
    }

    private static Footballer getFootballer3() {
        return new Footballer("333333", "testPlayer3", "testClub3", 30, 170);
    }

    private static Footballer getFootballer4() {
        return new Footballer("444444", "testPlayer3", "testClub3", 30, 170);
    }

    @Test
    @DisplayName("Should startup Spring")
    void shouldStartupSpring() {
    }

    // Should get all footballers --> return 2xx successful, check list size
    @Test
    @DisplayName("Should get all footballers")
    void shouldGetAllFootballers() throws Exception {
        repository.save(getFootballer1());
        repository.save(getFootballer2());
        repository.save(getFootballer3());
        this.mockMvc.perform(get("/footballers/"))
                .andDo(print())
                .andExpectAll(status().is2xxSuccessful(),
                        jsonPath("$").isArray(),
                        jsonPath("$", hasSize(3)));
    }

    // Should get a footballer by id --> return 2xx successful code, only one object
    @Test
    @DisplayName("Should get a footballer by id")
    void shouldGetFootballerById() throws Exception {
        repository.save(getFootballer1());
        repository.save(getFootballer2());
        int footballerToBeReturned = repository.save(getFootballer3()).getId();
        this.mockMvc.perform(get("/footballers/" + footballerToBeReturned))
                .andDo(print())
                .andExpectAll(status().isOk(),
                        jsonPath("$.pesel", is("333333")),
                        jsonPath("$.name", is("testPlayer3")),
                        jsonPath("$.club", is("testClub3")),
                        jsonPath("$.goals", is(30)),
                        jsonPath("$.height", is(170))
                );
    }

    // Should fail to get footballer by id -->return 4xx not found
    @Test
    @DisplayName("Should fail to get footballer by id")
    void shouldNotGetFootballerById() throws Exception {
        this.mockMvc.perform(get("/footballers/5432"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    // Should get footballers by name --> check if the list size is correct
    @Test
    @DisplayName("Should get footballers by name")
    void shouldGetFootballersByName() throws Exception {
        repository.save(getFootballer1());
        repository.save(getFootballer2());
        repository.save(getFootballer3());

        this.mockMvc.perform(get("/footballers/byName/?name=testPlayer1"))
                .andDo(print())
                .andExpectAll(status().is2xxSuccessful(),
                        jsonPath("$").isArray(),
                        jsonPath("$", hasSize(1))
                );
    }

    //Should get multiple footballers by name, check the size of the list and parameters with jsonPath
    @Test
    @DisplayName("Should get multiple footballers by name")
    void shouldGetMultipleFootballersByName() throws Exception {
        repository.save(getFootballer1());
        repository.save(getFootballer2());
        repository.save(getFootballer3());
        repository.save(getFootballer4());
        this.mockMvc.perform(get("/footballers/byName/?name=testPlayer3"))
                .andDo(print())
                .andExpectAll(status().is2xxSuccessful(),
                        jsonPath("$").isArray(),
                        jsonPath("$", hasSize(2)));
    }

    //Should not get any footballers by name --> check if the list is empty
    @Test
    @DisplayName("Should not get any footballers by name")
    void shouldNotGetAnyFootballersByName() throws Exception {
        repository.save(getFootballer1());
        repository.save(getFootballer2());
        repository.save(getFootballer3());
        this.mockMvc.perform(get("/footballers/byName/?name=xyz"))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$").isArray(),
                        jsonPath("$", hasSize(0))
                );
    }

    //Should add a footballer --> return 2xx created, check if the player was added and if he has the parameters we wanted
    @Test
    @DisplayName("Should add a footballer")
    void shouldAddFootballer() throws Exception {
        String request = """
                {
                "pesel":"333333",
                "name":"testPlayer3",
                "club":"testClub3",
                "goals":30,
                "height":170
                }
                """;

        this.mockMvc.perform(post("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isCreated(),
                        jsonPath("$").isMap(),
                        jsonPath("$.pesel", is("333333")),
                        jsonPath("$.name", is("testPlayer3")),
                        jsonPath("$.club", is("testClub3")),
                        jsonPath("$.goals", is(30)),
                        jsonPath("$.height", is(170))
                );
    }

    // Should fail to add a footballer -->  footballer not added, wrong body passed or the id already exists
    @Test
    @DisplayName("Should not add a footballer when pesel exists")
    void shouldNotAddFootballerWhenPeselExists() throws Exception {
        repository.save(getFootballer3());
        String request = """
                {
                "pesel":"333333",
                "name":"testPlayer3",
                "club":"testClub3",
                "goals":30,
                "height":170
                }
                """;

        this.mockMvc.perform(post("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest()
                );
    }

    // Should Get X Highest Footballers --> check if the list size is X
    @Test
    @DisplayName("Should get X highest footballers")
    void shouldGetXHighestFootballers() throws Exception {
        repository.save(getFootballer1());
        repository.save(getFootballer2());
        repository.save(getFootballer3());
        this.mockMvc.perform(get("/footballers/topXByHeight/?pageNumber=0&numberOfPlayers=2"))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$", hasSize(2)),
                        jsonPath("$[0].pesel", is("333333")),
                        jsonPath("$[0].name", is("testPlayer3")),
                        jsonPath("$[0].club", is("testClub3")),
                        jsonPath("$[0].goals", is(30)),
                        jsonPath("$[0].height", is(170)),

                        jsonPath("$[1].pesel", is("222222")),
                        jsonPath("$[1].name", is("testPlayer2")),
                        jsonPath("$[1].club", is("testClub2")),
                        jsonPath("$[1].goals", is(20)),
                        jsonPath("$[1].height", is(160))
                );
    }

    // Shouldn't get more than X footballers --> check if it doesn't return more footballers
    // than there are on a list/ doesn't duplicate
    @Test
    @DisplayName("Should not get more than X footballers")
    void shouldNotGetMoreThanXFootballers() throws Exception {
        repository.save(getFootballer1());
        repository.save(getFootballer2());
        repository.save(getFootballer3());
        this.mockMvc.perform(get("/footballers/topXByHeight/?pageNumber=0&numberOfPlayers=20"))
                .andDo(print())
                .andExpectAll(jsonPath("$", hasSize(3)),
                        jsonPath("$[0].pesel", is("333333")),
                        jsonPath("$[0].name", is("testPlayer3")),
                        jsonPath("$[0].club", is("testClub3")),
                        jsonPath("$[0].goals", is(30)),
                        jsonPath("$[0].height", is(170)),

                        jsonPath("$[1].pesel", is("222222")),
                        jsonPath("$[1].name", is("testPlayer2")),
                        jsonPath("$[1].club", is("testClub2")),
                        jsonPath("$[1].goals", is(20)),
                        jsonPath("$[1].height", is(160))
                );
    }

    //     Should update a footballer --> return 2xx successful, footballer exists with new data
    @Test
    @DisplayName("Should update footballer")
    void shouldUpdateFootballer() throws Exception {
        repository.save(getFootballer1());
        int footballerIdToBeUpdated = repository.save(getFootballer2()).getId();

        String request = """
                {
                "id":footballer.id,
                "pesel":"333333",
                "name":"testPlayer3",
                "club":"testClub3",
                "goals":30,
                "height":170
                }
                """.
                replace("footballer.id", String.valueOf(footballerIdToBeUpdated));

        this.mockMvc.perform(put("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(
                        jsonPath("$").isMap(),
                        jsonPath("$.pesel", is("333333")),
                        jsonPath("$.name", is("testPlayer3")),
                        jsonPath("$.club", is("testClub3")),
                        jsonPath("$.goals", is(30)),
                        jsonPath("$.height", is(170))
                );
    }

    //         Should fail to update a footballer --> return 4xx not found, footballer with that id doesn't exist
    @Test
    @DisplayName("Should fail to update footballer")
    void shouldNotUpdateFootballerWhenIdDoesntExist() throws Exception {
        repository.save(getFootballer1());
        int footballerIdToBeUpdated = repository.save(getFootballer2()).getId();

        String request = """
                {
                "id":footballer.id,
                "pesel":"333333",
                "name":"testPlayer3",
                "club":"testClub3",
                "goals":30,
                "height":170
                }
                """.
                replace("footballer.id", String.valueOf(footballerIdToBeUpdated + 1000));

        this.mockMvc.perform(put("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$").doesNotExist());
    }

    // Should delete a footballer --> return 2xx, check if footballer with that id doesn't exist anymore
    @Test
    @DisplayName("Should delete a footballer")
    void shouldDeleteFootballer() throws Exception {
        repository.save(getFootballer1());
        int footballerToBeDeleted = repository.save(getFootballer2()).getId();
        repository.save(getFootballer3());
        this.mockMvc.perform(delete("/footballers/" + footballerToBeDeleted))
                .andDo(print())
                .andExpectAll(status().is2xxSuccessful(),
                        jsonPath("$").doesNotExist()
                );
        Assertions.assertFalse(repository.existsById(footballerToBeDeleted));
    }

    // Should fail to delete a footballer --> footballer with that id doesn't exist already,
// check the size of the footballerList before and after deletion
    @Test
    @DisplayName("Should not delete a footballer when id doesn't exist")
    void shouldNotDeleteFootballerWhenIdDoesntExist() throws Exception {
        repository.save(getFootballer1());
        int footballerToBeDeleted = repository.save(getFootballer2()).getId();
        footballerToBeDeleted += 100;
        this.mockMvc.perform(delete("/footballers/" + footballerToBeDeleted))
                .andDo(print())
                .andExpectAll(jsonPath("$").doesNotExist()
                );
    }

    //Should not add a footballer when pesel is not passed
    @Test
    @DisplayName("Should not add footballer when pesel is not passed")
    void shouldNotAddFootballerWhenPeselIsNotPassed() throws Exception {
        String request = """
                {
                "name":"testPlayer3",
                "club":"testClub3",
                "goals":30,
                "height":170
                }
                """;
        this.mockMvc.perform(post("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        content().string("You have to provide a pesel !"));
    }

    //Should not add a footballer when name is not passed
    @Test
    @DisplayName("Should not add footballer when name is not passed")
    void shouldNotAddFootballerWhenNameIsNotPassed() throws Exception {
        String request = """
                {
                "pesel":"333333",
                "club":"testClub3",
                "goals":30,
                "height":170
                }
                """;
        this.mockMvc.perform(post("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        content().string("You have to provide a name !"));
    }

    //Should not add a footballer when height is not passed
    @Test
    @DisplayName("Should not add footballer when height is not passed")
    void shouldNotAddFootballerWhenHeightIsNotPassed() throws Exception {
        String request = """
                {
                "pesel":"333333",
                "name":"testPlayer3",
                "club":"testClub3",
                "goals":30
                }
                """;
        this.mockMvc.perform(post("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        content().string("You have to provide a footballer height !"));
    }

    //Should not add a footballer when pesel is not passed
    @Test
    @DisplayName("Should not add footballer when pesel is not passed")
    void shouldNotUpdateFootballerWhenPeselIsNotPassed() throws Exception {
        repository.save(getFootballer1());
        int footballerIdToBeUpdated = repository.save(getFootballer2()).getId();
        String request = """
                {
                "id":footballer.id,
                "name":"testPlayer3",
                "club":"testClub3",
                "goals":30,
                "height":170
                }
                """.
                replace("footballer.id", String.valueOf(footballerIdToBeUpdated));

        this.mockMvc.perform(put("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        content().string("You have to provide a pesel !"));
    }

    //Should not add a footballer when name is not passed
    @Test
    @DisplayName("Should not add footballer when name is not passed")
    void shouldNotUpdateFootballerWhenNameIsNotPassed() throws Exception {
        repository.save(getFootballer1());
        int footballerIdToBeUpdated = repository.save(getFootballer2()).getId();
        String request = """
                {
                "id":footballer.id,
                "pesel":"333333",
                "club":"testClub3",
                "goals":30,
                "height":170
                }
                """.
                replace("footballer.id", String.valueOf(footballerIdToBeUpdated));

        this.mockMvc.perform(put("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        content().string("You have to provide a name !"));
    }

    //Should not add a footballer when height is not passed
    @Test
    @DisplayName("Should not add footballer when height is not passed")
    void shouldNotUpdateFootballerWhenHeightIsNotPassed() throws Exception {
        repository.save(getFootballer1());
        int footballerIdToBeUpdated = repository.save(getFootballer2()).getId();
        String request = """
                {
                "id":footballer.id,
                "pesel":"333333",
                "name":"testPlayer3",
                "club":"testClub3",
                "goals":30
                }
                """.
                replace("footballer.id", String.valueOf(footballerIdToBeUpdated));

        this.mockMvc.perform(put("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        content().string("You have to provide a footballer height !"));
    }
}
