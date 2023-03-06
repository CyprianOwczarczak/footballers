package com.owczarczak.footballers.club;

import com.owczarczak.footballers.contract.ContractRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class ClubControllerTest {
    //TODO add JsonPatch assertions

    @Autowired
    ClubRepository repository;

    @Autowired
    MockMvc mockMvc;

    @Test
    void shouldGetAllClubsWhichPlayedMoreThan3Matches() throws Exception {
        this.mockMvc.perform(get("/clubs/MoreThan3Matches"))
                .andDo(print());
    }
}
