package com.owczarczak.footballers.footballer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WebAppConfiguration
@AutoConfigureMockMvc
class FootballerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Should startup Spring")
    void shouldStartupSpring() {
    }

    @Test
    void shouldGetAllFootballers() throws Exception {
        this.mockMvc.perform(get("/footballers/"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

    }

}