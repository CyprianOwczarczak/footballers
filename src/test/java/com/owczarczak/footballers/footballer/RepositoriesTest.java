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
import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
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

    //Add a Contract to
    @Test
    @DisplayName("Should add a contract to the existing footballer")
    public void shouldAddContractToTheExistingFootballer() {
        //Creating example clubs
        Club club1 = new Club("ClubFromMatch13", Instant.now(), Collections.emptyList());

        //Creating example footballers
        Footballer footballer = new Footballer("100002", "ExampleFootballerScore", 80, 230);

        Contract contract = new Contract(club1, footballer, Instant.EPOCH, Instant.now(), 10000);
        contractRepository.save(contract);
    }

    //    Za jednym razem zapisać mecz z reprezentacjami, piłkarzami i golami, piłkarzy
//    i reprezentację klubów pobrać ze wcześniej zapisanych danych, dodać tych piłkarzy do reprezentacji klubu
    @Test
    @DisplayName("Should add a new match with new representations with already existing footballers and with new scores")
    public void shouldAddNewMatchWithNewRepresentationsWithAlreadyExistingFootballers() {
        //Get existing footballers from the database
        Optional<Footballer> footballerOptional1 = footballerRepository.findById(1);
        Optional<Footballer> footballerOptional2 = footballerRepository.findById(5);
        Footballer footballerForRepresentation1 = footballerOptional1.get();
        Footballer footballerForRepresentation2 = footballerOptional2.get();

        //Get existing Clubs from the database
        // -- it didn't work because we used Cascade.PERSIST in the ClubRepresentation which tried to save the existing club
        Optional<Club> clubOptional1 = clubRepository.findById(1);
        Optional<Club> clubOptional2 = clubRepository.findById(2);
        Club club1 = clubOptional1.get();
        Club club2 = clubOptional2.get();

        //Create new ClubRepresentations -- it didn't work because the Cascade.PERSIST tried to save
        ClubRepresentation clubRepresentation1 = new ClubRepresentation(club1, List.of(footballerForRepresentation1));
        ClubRepresentation clubRepresentation2 = new ClubRepresentation(club2, List.of(footballerForRepresentation2));

        //Add new Match without saving it to the database yet (we need it to set Scores to this match)
//        matchRepository.save(match);
        Match match = new Match(clubRepresentation1, clubRepresentation2,
                "Referee MatchWithExistingClubs", Instant.now(), Collections.emptyList());

        //Add a few new Scores for that match
        Score score1 = new Score(match, footballerForRepresentation1, 20);
        Score score2 = new Score(match, footballerForRepresentation1, 40);
        Score score3 = new Score(match, footballerForRepresentation2, 60);

        //Now we can set Score list for the match and save it to the database
        match.setScores(List.of(score1, score2, score3));
        matchRepository.save(match);
    }

    //Istniejący mecz z punktami, reprezentacjami itd.  aktualizuje sędziego
    @Test
    @DisplayName("Should update referee name of an existing match")
    public void shoudUpdateRefereeNameOfExistingMatch() {
        //Get an existing match
        Optional<Match> matchOptional = matchRepository.findById(19);
        Match match = matchOptional.get();

        //Change the nameOfReferee and save to database
        match.setNameOfReferee("ChangedName Referee");
        matchRepository.save(match);
    }

    //Do istniejącego meczu dodać gole (Dwustronna relacja ! --> dodać "score" do meczu oraz mecz do score
    @Test
    @DisplayName("Should add a goal to an existing match")
    @Transactional
    @Commit
    public void shouldAddGoalToExistingMatch() {
        //Get an existing match
        Optional<Match> matchOptional = matchRepository.findById(19);
        Match match = matchOptional.get();

        //Get an existing foootballer
        Optional<Footballer> footballerOptional = footballerRepository.findById(1);
        Footballer footballer = footballerOptional.get();

        //Create a new Score object
        Score scoreToAdd = new Score(match, footballer, 10);

        //Get current Score list and update it with a new Score object
        List<Score> scoreList = match.getScores();
        scoreList.add(scoreToAdd);

        matchRepository.save(match);
    }

    @Test
    public void shouldThrowLazyInitializationException() {
        //Get an existing Club
        Optional<Club> clubOptional = clubRepository.findById(4);
        Club club = clubOptional.get();

        //Get an existing Contract to add to the contractList
        Optional<Contract> contractOptional = contractRepository.findById(9);
        Contract contract = contractOptional.get();

        //Try to add a Contract to existing contractList of a Club (through Bag
        List<Contract> contractList = club.getContractList();
        Assertions.assertThrows(LazyInitializationException.class, () -> contractList.add(contract));
    }

    //Zaktualizować clubRepresentation w istniejącym meczu
    @Test
    public void shouldUpdateClubRepresentationInExistingMatch() {
        //Get an existing match
        Optional<Match> matchOptional = matchRepository.findById(1);
        Match match = matchOptional.get();

        //Get an existing club to add to clubRepresentation
        Optional<Club> clubOptional = clubRepository.findById(10);
        Club club = clubOptional.get();

        //Get existing footballers to add to clubRepresentation
        Optional<Footballer> footballerOptional1 = footballerRepository.findById(3);
        Footballer footballer1 = footballerOptional1.get();
        Optional<Footballer> footballerOptional2 = footballerRepository.findById(4);
        Footballer footballer2 = footballerOptional2.get();

        //Create new ClubRepresentation to update the match with
        ClubRepresentation clubRepresentation = new ClubRepresentation(club, List.of(footballer1, footballer2));
        clubRepresentationRepository.save(clubRepresentation); //Without save it won't work

        //Set the Host representation to a new object
        match.setHost(clubRepresentation);
        matchRepository.save(match);
    }

    @Test
    public void addClubRepresentationToTheExistingMatch() {
        //Get an existing match
        Optional<Match> matchOptional = matchRepository.findById(5);
        Match matchToAddRepresentation = matchOptional.get();

        //Create new club
        Club club1 = new Club("ClubFromMatch12", Instant.now(), Collections.emptyList());

        //Create new club representation
        ClubRepresentation clubRepresentation = new ClubRepresentation(club1, Collections.emptyList());
        matchToAddRepresentation.setHost(clubRepresentation);
    }

    @Test
    public void shouldSaveMatchWithRepresentationsFootballersAndScoresAtOnce() {
        Match match = new Match();
        matchRepository.save(match);
    }


    //////////Repository Queries///////////
    @Test
    public void shouldGetAllContractsOrderedBySalary() {
        Pageable pageable = PageRequest.of(0, 5);
        contractRepository.findAllByOrderBySalaryDesc(pageable);
    }

    @Test
    public void shouldGetClubsByName() {
        clubRepository.findByName("Barcelona");
    }

    @Test
    public void shouldGetAllClubsPageable() {
        Pageable pageable = PageRequest.of(0, 5);
        clubRepository.findAllByOrderByCreatedDesc(pageable);
    }

    @Test
    public void shouldGetAllClubsThatPlayedMoreThan3Matches() {
        List<Club> clubRepresentationList = clubRepository.findAllClubsWhichPlayedMoreThan3Matches();
        Assertions.assertEquals(3, clubRepresentationList.size());
    }

//    @Test
//    public void shouldGetAverageFootballerGoalsWhenTheirContractIsShorterThanYear() {
////        Instant start = Instant.from(LocalDate.of(2020, 1, 1));
//        Instant start = Instant.now().minus(3, ChronoUnit.YEARS);
//        Instant end = Instant.now();
//
//        List<Double> meanGoals = contractRepository.getMeanGoalsScoredByFootballersDuringContractLastingLessThanYear(
//                start, end
//        );
//        Assertions.assertEquals(20, meanGoals.get(0));
//    }

    @Test
    public void shouldGetAvgGoalsPerMatchForFootballer() {
        List<Double> obj = contractRepository.getAvgGoalsPerMatchForFootballer();
        System.out.println(obj);
    }

    @Test
    public void shouldGetMeanLengthOfContractsInClub() {
        //Get existing Club from the database
        Optional<Club> clubOptional = clubRepository.findById(4);
        Club club = clubOptional.get();

        contractRepository.getMeanLenghtOfContractsInClub(club);
    }

//////////The end of repository queries//////////

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
