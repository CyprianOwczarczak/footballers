package com.owczarczak.footballers.match;

import com.owczarczak.footballers.TestDataFactory;
import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
public class MatchControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    ClubRepresentationRepository representationRepository;

    @Autowired
    ClubRepository clubRepository;

    @Test
    void shouldFindRefereeByName() throws Exception {

        //Creating example matches
        List<Club> clubList = clubRepository.saveAll(TestDataFactory.getClubList1());
        List<ClubRepresentation> representationList = representationRepository.saveAll(TestDataFactory.getRepresentationList1(clubList));
        matchRepository.saveAll(TestDataFactory.getMatchList(representationList));

        this.mockMvc.perform(get("/matches/byRefereeName/"))
                .andDo(print());


//        this.userClientObject = client.createClient();
//        mockMvc.perform(get("/byName")
//                .sessionAttr("userClientObject", this.userClientObject)
//                .param("firstName", firstName)
//                .param("lastName", lastName)
//        ).andDo(print())
    }
}
