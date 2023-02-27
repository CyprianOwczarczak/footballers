package com.owczarczak.footballers.footballer;

import com.owczarczak.footballers.club.ClubRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class ClubControllerTest {

    @Autowired
    ClubRepository repository;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void shouldGetClubsSortedByName() throws Exception {
        repository.findAllClubsSortByName();
    }


//    @Test
//    @DisplayName("Should get all footballers")
//    void shouldGetAllFootballers() throws Exception {
//        repository.save(getFootballer1());
//        repository.save(getFootballer2());
//        repository.save(getFootballer3());
//        this.mockMvc.perform(get("/footballers/"))
//                .andDo(print())
//                .andExpectAll(getJsonValidationRules(3));
//    }
}
