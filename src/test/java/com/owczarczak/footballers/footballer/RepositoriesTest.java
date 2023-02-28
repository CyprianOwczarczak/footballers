package com.owczarczak.footballers.footballer;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import com.owczarczak.footballers.contract.Contract;
import com.owczarczak.footballers.contract.ContractRepository;
import com.owczarczak.footballers.match.Match;
import com.owczarczak.footballers.match.MatchRepository;
import com.owczarczak.footballers.score.Score;
import com.owczarczak.footballers.score.ScoreRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class RepositoriesTest {

    @Autowired
    ClubRepository clubRepository;
    @Autowired
    ClubRepresentationRepository clubRepresentationRepository;
    @Autowired
    ContractRepository contractRepository;
    @Autowired
    MatchRepository matchRepository;
    @Autowired
    ScoreRepository scoreRepository;
    @Autowired
    FootballerRepository footballerRepository;

    @Test
    public void shouldAddClub() {
        List<Contract> contractList = new LinkedList<>();
        Club exampleClub = new Club("ExampleClub", Instant.EPOCH, contractList);
        clubRepository.save(exampleClub);
    }

    @Test
    public void shouldAddClubRepresentation() {
        Club club1 = new Club("ClubRepresentationExampleClub5", Instant.now(), Collections.emptyList());

        Footballer footballer1 = new Footballer("10002", "ExampleFootballer3", 50, 250);
        Footballer footballer2 = new Footballer("10003", "ExampleFootballer4", 60, 300);
        List<Footballer> footballerList = List.of(footballer1, footballer2);

        ClubRepresentation exampleClubRepresentation = new ClubRepresentation(club1, footballerList);
        clubRepresentationRepository.save(exampleClubRepresentation);
    }

    @Test
    public void shouldAddContract() {
        Club club1 = new Club("ClubFromContract", Instant.now(), Collections.emptyList());
        Footballer footballer1 = new Footballer("1100", "ExampleFootballer1", 50, 250);

        Contract contract1 = new Contract(club1, footballer1, Instant.EPOCH, Instant.EPOCH, 10000);
        contractRepository.save(contract1);
    }

    @Test
    public void shouldAddMatch() {
        //Create examples Clubs
        Club club1 = new Club("ClubFromMatch1", Instant.now(), Collections.emptyList());
        Club club2 = new Club("ClubFromMatch2", Instant.now(), Collections.emptyList());

        //Create example ClubRepresentations
        ClubRepresentation clubRepresentation1 = new ClubRepresentation(club1, Collections.emptyList());
        ClubRepresentation clubRepresentation2 = new ClubRepresentation(club2, Collections.emptyList());

        Match match1 = new Match(clubRepresentation1, clubRepresentation2,
                "ExampleRefereeMatch", Instant.now(), Collections.emptyList());
        matchRepository.save(match1);
    }

    @Test
    public void shouldAddScore() {
        //Create example clubs
        Club club1 = new Club("ClubFromMatch12", Instant.now(), Collections.emptyList());
        Club club2 = new Club("ClubFromMatch13", Instant.now(), Collections.emptyList());

        //Create example representationClubs
        ClubRepresentation clubRepresentation1 = new ClubRepresentation(club1, Collections.emptyList());
        ClubRepresentation clubRepresentation2 = new ClubRepresentation(club2, Collections.emptyList());

        //Create example match
        Match match = new Match(clubRepresentation1, clubRepresentation2,
                "ExampleRefereeScore", Instant.now(), Collections.emptyList());

        //Create example footballer
        Footballer footballer = new Footballer("22332233", "ExampleFootballerScore", 80, 230);

        Score score = new Score(match, footballer, 30);
        scoreRepository.save(score);
    }

    //Add goals to the existing match, assign the goal to the existing footballer
    @Test
    public void shouldAddGoalToTheExistingFootballerInExistingMatch() {
        Optional<Match> matchOptional = matchRepository.findById(5);
        Optional<Footballer> footballerOptional = footballerRepository.findById(8);

        Match matchToAddScore = matchOptional.get();
        Footballer footballerToAddScore = footballerOptional.get();

        Score score = new Score(matchToAddScore, footballerToAddScore, 40);
        scoreRepository.save(score);
    }

    @Test
    public void shouldGetAllClubs() {
        List<Club> clubList = clubRepository.findAll();
        for (Club value : clubList) {
            System.out.println(value.getName());
        }
    }

    @Test
    public void shouldGetAllClubRepresentations() {
        List<ClubRepresentation> clubRepresentationList = clubRepresentationRepository.findAll();
        for (ClubRepresentation value : clubRepresentationList) {
            System.out.println(value.getId());
        }
    }

    @Test
    public void shouldGetAllContracts() {
        List<Contract> contractList = contractRepository.findAll();
        for (Contract value : contractList) {
            System.out.println(value.getId());
            System.out.println(value.getSalary());
        }
    }

    @Test
    public void shouldGetAllMatches() {
        List<Match> matchList = matchRepository.findAll();
        for (Match value : matchList) {
            System.out.println(value.getId());
            System.out.println(value.getNameOfReferee());
        }
    }

    @Test
    public void shouldGetAllScores() {
        List<Score> scoreList = scoreRepository.findAll();
        for (Score value : scoreList) {
            System.out.println(value.getId());
            System.out.println(value.getMinuteScored());
        }
    }


}
