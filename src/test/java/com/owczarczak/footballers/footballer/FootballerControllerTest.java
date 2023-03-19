package com.owczarczak.footballers.footballer;

import com.owczarczak.footballers.TestDataFactory;
import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class FootballerControllerTest {

    @Autowired
    FootballerRepository footballerRepository;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ClubRepresentationRepository representationRepository;

    @AfterEach
    void setup() {
        representationRepository.deleteAll();
        clubRepository.deleteAll();
        footballerRepository.deleteAll();
    }

    @Autowired
    FootballerService service;

    @Test
    @DisplayName("Should get all footballers")
    void shouldGetAllFootballers() throws Exception {
        footballerRepository.save(getFootballer1());
        footballerRepository.save(getFootballer2());
        footballerRepository.save(getFootballer3());
        this.mockMvc.perform(get("/footballers/"))
                .andDo(print())
                .andExpectAll(getJsonValidationRules(3));
    }

    @Test
    @DisplayName("Should get a footballer by id")
    void shouldGetFootballerById() throws Exception {
        footballerRepository.save(getFootballer1());
        footballerRepository.save(getFootballer2());

        int footballerToBeReturned = footballerRepository.save(getFootballer3()).getId();
        this.mockMvc.perform(get("/footballers/" + footballerToBeReturned))
                .andDo(print())
                .andExpectAll(getJsonValidationRules());
    }

    @Test
    @DisplayName("Should fail to get footballer by id")
    void shouldNotGetFootballerById() throws Exception {
        this.mockMvc.perform(get("/footballers/5432"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should get footballers by name")
    void shouldGetFootballersByName() throws Exception {
        footballerRepository.save(getFootballer1());
        footballerRepository.save(getFootballer2());
        footballerRepository.save(getFootballer3());

        this.mockMvc.perform(get("/footballers/byName/?name=testPlayer1"))
                .andDo(print())
                .andExpectAll(getJsonValidationRules(1));
    }

    @Test
    @DisplayName("Should get multiple footballers by name")
    void shouldGetMultipleFootballersByName() throws Exception {
        footballerRepository.save(getFootballer1());
        footballerRepository.save(getFootballer2());
        footballerRepository.save(getFootballer3());
        footballerRepository.save(getFootballer4());
        this.mockMvc.perform(get("/footballers/byName/?name=testPlayer3"))
                .andDo(print())
                .andExpectAll(getJsonValidationRules(2));
    }

    @Test
    @DisplayName("Should not get any footballers by name")
    void shouldNotGetAnyFootballersByName() throws Exception {
        footballerRepository.save(getFootballer1());
        footballerRepository.save(getFootballer2());
        footballerRepository.save(getFootballer3());
        this.mockMvc.perform(get("/footballers/byName/?name=xyz"))
                .andDo(print())
                .andExpectAll(getJsonValidationRules(0));
    }

    @Test
    @DisplayName("Should add a footballer")
    void shouldAddFootballer() throws Exception {
        String request = """
                {
                "pesel":"333333",
                "name":"testPlayer3",
                "height":170
                }
                """;

        this.mockMvc.perform(post("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isCreated(),
                        jsonPath("$").isMap())
                .andExpectAll(getJsonValidationRules());
    }

    @Test
    @DisplayName("Should not add a footballer when pesel exists")
    void shouldNotAddFootballerWhenPeselExists() throws Exception {
        footballerRepository.save(getFootballer3());
        String request = """
                {
                "pesel":"333333",
                "name":"testPlayer3",
                "height":170
                }
                """;

        this.mockMvc.perform(post("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest());
    }

    @Test
    @DisplayName("Should get X highest footballers")
    void shouldGetXHighestFootballers() throws Exception {
        footballerRepository.save(getFootballer1());
        footballerRepository.save(getFootballer2());
        footballerRepository.save(getFootballer3());
        this.mockMvc.perform(get("/footballers/topXByHeight/?pageNumber=0&numberOfPlayers=2"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpectAll(getJsonArrayValidationRules());
    }

    @Test
    @DisplayName("Should not get more than X footballers")
    void shouldNotGetMoreThanXFootballers() throws Exception {
        footballerRepository.save(getFootballer1());
        footballerRepository.save(getFootballer2());
        footballerRepository.save(getFootballer3());
        this.mockMvc.perform(get("/footballers/topXByHeight/?pageNumber=0&numberOfPlayers=20"))
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpectAll(getJsonArrayValidationRules());
    }

    @Test
    @DisplayName("Should update footballer")
    void shouldUpdateFootballer() throws Exception {
        footballerRepository.save(getFootballer1());
        int footballerIdToBeUpdated = footballerRepository.save(getFootballer2()).getId();

        String request = """
                {
                "id":footballer.id,
                "pesel":"333333",
                "name":"testPlayer3",
                "height":170
                }
                """.
                replace("footballer.id", String.valueOf(footballerIdToBeUpdated));

        this.mockMvc.perform(put("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpect(
                        jsonPath("$").isMap())
                .andExpectAll(getJsonValidationRules());
    }

    @Test
    @DisplayName("Should fail to update footballer")
    void shouldNotUpdateFootballerWhenIdDoesntExist() throws Exception {
        footballerRepository.save(getFootballer1());
        int footballerIdToBeUpdated = footballerRepository.save(getFootballer2()).getId();

        String request = """
                {
                "id":footballer.id,
                "pesel":"333333",
                "name":"testPlayer3",
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

    @Test
    @DisplayName("Should delete a footballer")
    void shouldDeleteFootballer() throws Exception {
        footballerRepository.save(getFootballer1());
        int footballerToBeDeleted = footballerRepository.save(getFootballer2()).getId();
        footballerRepository.save(getFootballer3());
        this.mockMvc.perform(delete("/footballers/" + footballerToBeDeleted))
                .andDo(print())
                .andExpectAll(status().is2xxSuccessful(),
                        jsonPath("$").doesNotExist()
                );
        Assertions.assertFalse(footballerRepository.existsById(footballerToBeDeleted));
    }

    @Test
    @DisplayName("Should not delete a footballer when id doesn't exist")
    void shouldNotDeleteFootballerWhenIdDoesntExist() throws Exception {
        footballerRepository.save(getFootballer1());
        int footballerToBeDeleted = footballerRepository.save(getFootballer2()).getId();
        footballerToBeDeleted += 100;
        this.mockMvc.perform(delete("/footballers/" + footballerToBeDeleted))
                .andDo(print())
                .andExpectAll(jsonPath("$").doesNotExist()
                );
    }

    @Test
    @DisplayName("Should not add footballer when pesel, name and height is not provided")
    void shouldNotAddFootballerWhenPeselAndNameAndHeightIsNotProvided() throws Exception {
        String request = """
                {
                }
                """;
        this.mockMvc.perform(post("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        content().string("""
                                ["You have to provide a pesel !","You have to provide a name !","You have to provide height !"]"""));
    }

    @Test
    @DisplayName("Should not add footballer when pesel is not provided")
    void shouldNotAddFootballerWhenPeselIsNotProvided() throws Exception {
        String request = """
                {
                "name":"testPlayer3",
                "height":170
                }
                """;
        this.mockMvc.perform(post("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        content().string("""
                                ["You have to provide a pesel !"]"""));
    }

    @Test
    @DisplayName("Should not add footballer when name is not provided")
    void shouldNotAddFootballerWhenNameIsNotProvided() throws Exception {
        String request = """
                {
                "pesel":"333333",
                "height":170
                }
                """;
        this.mockMvc.perform(post("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        content().string("""
                                ["You have to provide a name !"]"""));
    }

    @Test
    @DisplayName("Should not add footballer when height is not provided")
    void shouldNotAddFootballerWhenHeightIsNotProvided() throws Exception {
        String request = """
                {
                "pesel":"333333",
                "name":"testPlayer3"
                }
                """;
        this.mockMvc.perform(post("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        content().string("""
                                ["You have to provide height !"]"""));
    }

    @Test
    @DisplayName("Should not update footballer when pesel, name and height is not provided")
    void shouldNotUpdateFootballerWhenPeselAndNameAndHeightIsNotProvided() throws Exception {
        footballerRepository.save(getFootballer1());
        int footballerIdToBeUpdated = footballerRepository.save(getFootballer2()).getId();
        String request = """
                {
                "id":footballer.id
                }
                """.
                replace("footballer.id", String.valueOf(footballerIdToBeUpdated));

        this.mockMvc.perform(put("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        content().string("[\"You have to provide a pesel !\",\"You have to provide a name !\",\"You have to provide height !\"]"));
    }

    @Test
    @DisplayName("Should not update footballer when pesel is not provided")
    void shouldNotUpdateFootballerWhenPeselIsNotProvided() throws Exception {
        footballerRepository.save(getFootballer1());
        int footballerIdToBeUpdated = footballerRepository.save(getFootballer2()).getId();
        String request = """
                {
                "id":footballer.id,
                "name":"testPlayer3",
                "height":170
                }
                """.
                replace("footballer.id", String.valueOf(footballerIdToBeUpdated));

        this.mockMvc.perform(put("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        content().string("""
                                ["You have to provide a pesel !"]"""));
    }

    @Test
    @DisplayName("Should not update footballer when name is not provided")
    void shouldNotUpdateFootballerWhenNameIsNotProvided() throws Exception {
        footballerRepository.save(getFootballer1());
        int footballerIdToBeUpdated = footballerRepository.save(getFootballer2()).getId();
        String request = """
                {
                "id":footballer.id,
                "pesel":"333333",
                "height":170
                }
                """.
                replace("footballer.id", String.valueOf(footballerIdToBeUpdated));

        this.mockMvc.perform(put("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        content().string("""
                                ["You have to provide a name !"]"""));
    }

    @Test
    @DisplayName("Should not update footballer when height is not provided")
    void shouldNotUpdateFootballerWhenHeightIsNotProvided() throws Exception {
        footballerRepository.save(getFootballer1());
        int footballerIdToBeUpdated = footballerRepository.save(getFootballer2()).getId();
        String request = """
                {
                "id":footballer.id,
                "name":"testPlayer3",
                "pesel":"333333"
                }
                """.
                replace("footballer.id", String.valueOf(footballerIdToBeUpdated));

        this.mockMvc.perform(put("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isBadRequest(),
                        content().string("""
                                ["You have to provide height !"]"""));
    }

    @Test
    @DisplayName("Should not update footballer when id is not provided")
    void shouldNotUpdateFootballerWhenIdIsNotProvided() throws Exception {
        footballerRepository.save(getFootballer1());
        int footballerIdToBeUpdated = footballerRepository.save(getFootballer2()).getId();
        String request = """
                {
                "name":"testPlayer3",
                "pesel":"333333",
                "height":170
                }
                """.
                replace("footballer.id", String.valueOf(footballerIdToBeUpdated));

        this.mockMvc.perform(put("/footballers/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andDo(print())
                .andExpectAll(status().isNotFound());
    }
//    whichFootballersPlayedInMostMatches
    @Transactional
    @Rollback(value = false)
    @Test
    void shouldReturnFootballersWhoPlayedInMostMatches() throws Exception {
        //Jeden klub, jedna reprezentacja, jeden zawodnik

        Club club = new Club("ExampleClub", Instant.now());
        Club clubReturned = clubRepository.save(club);
        ClubRepresentation clubRepresentation = new ClubRepresentation(clubReturned, Collections.emptyList());
        ClubRepresentation representationReturned = representationRepository.save(clubRepresentation);

        //Add representation to the Set
        Set<ClubRepresentation> representationsList = Set.of(representationReturned);

        //Saving the Footballer with clubRepresentation
        Footballer footballer = new Footballer("000000", "ExampleFootballer", 200, representationsList);

        // 1) Zapisać dla każdej reprezentacji zawodnika
        Footballer footballerReturned = footballerRepository.save(footballer);
        for (ClubRepresentation representation: representationsList) {
            representation.setFootballerList(List.of(footballerReturned));
        }

        // 2) Każdej reprezentacji ustawić zawodnika
        footballer.setRepresentationList(representationsList);

        //Relating the ClubRepresentation back to Footballer
        representationReturned.setFootballerList(List.of(footballerReturned));

//footballerRepository.save(footballerReturned);
//representationRepository.save(representationReturned);
//TODO dlaczego to działa --> książka JPA

//        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList1());
//        footballerRepository.saveAll(TestDataFactory.getFootballerListWithRepresentations(clubList));

        this.mockMvc.perform(get("/footballers/mostMatchesPlayed"))
                .andDo(print())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].representationSize", is(1)))
                ;
    }

    private static Footballer getFootballer1() {
        return new Footballer("111111", "testPlayer1", 150);
    }

    private static Footballer getFootballer2() {
        return new Footballer("222222", "testPlayer2", 160);
    }

    private static Footballer getFootballer3() {
        return new Footballer("333333", "testPlayer3", 170);
    }

    private static Footballer getFootballer4() {
        return new Footballer("444444", "testPlayer3", 170);
    }

    private static ResultMatcher[] getJsonValidationRules() {
        //1. Utwórz listę
        List<ResultMatcher> result = new ArrayList<>();

        //2. Dodaj dane do listy ResultMatcher
        result.add(jsonPath("$.pesel", is("333333")));
        result.add(jsonPath("$.name", is("testPlayer3")));
        result.add(jsonPath("$.height", is(170)));

        //3. Przerób listę na tablicę + zwróc listę
        return result.toArray(new ResultMatcher[0]);
    }

    private static ResultMatcher[] getJsonValidationRules(int arraySize) {
        //1. Utwórz listę
        List<ResultMatcher> result = new ArrayList<>();

        //2. Dodaj dane do listy ResultMatcher
        result.add(status().isOk());
        result.add(jsonPath("$").isArray());
        result.add(jsonPath("$", hasSize(arraySize)));

        //3. Przerób listę na tablicę + zwróc listę
        return result.toArray(new ResultMatcher[0]);
    }

    private static ResultMatcher[] getJsonArrayValidationRules() {
        //1. Utwórz listę
        List<ResultMatcher> result = new ArrayList<>();

        //2. Dodaj dane do listy ResultMatcher
        result.add(jsonPath("$[0].pesel", is("333333")));
        result.add(jsonPath("$[0].name", is("testPlayer3")));
        result.add(jsonPath("$[0].height", is(170)));

        result.add(jsonPath("$[1].pesel", is("222222")));
        result.add(jsonPath("$[1].name", is("testPlayer2")));
        result.add(jsonPath("$[1].height", is(160)));

        //3. Przerób listę na tablicę + zwróc listę
        return result.toArray(new ResultMatcher[0]);
    }
}
