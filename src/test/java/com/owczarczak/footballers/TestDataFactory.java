package com.owczarczak.footballers;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import com.owczarczak.footballers.contract.Contract;
import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.match.Match;
import com.owczarczak.footballers.score.Score;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static java.time.LocalDateTime.of;

public class TestDataFactory {

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ClubRepresentationRepository representationRepository;

    public static List<Club> getClubList1() {
        List<Club> clubList = new LinkedList<>();

        //Creating dates of founding the clubs
        LocalDateTime localDateTime1 = of(2015, 1, 5, 1, 10, 0);
        LocalDateTime localDateTime2 = of(2016, 2, 10, 2, 20, 0);
        LocalDateTime localDateTime3 = of(2017, 3, 15, 3, 30, 0);
        LocalDateTime localDateTime4 = of(2018, 4, 20, 4, 40, 0);
        LocalDateTime localDateTime5 = of(2019, 5, 25, 5, 50, 0);

        //Adding Clubs to the list
        Collections.addAll(clubList,
                new Club("Barcelona", localDateTime1.toInstant(ZoneOffset.UTC)),
                new Club("Lech", localDateTime2.toInstant(ZoneOffset.UTC)),
                new Club("Real", localDateTime3.toInstant(ZoneOffset.UTC)),
                new Club("Manchester", localDateTime4.toInstant(ZoneOffset.UTC)),
                new Club("Bayern", localDateTime5.toInstant(ZoneOffset.UTC)));

        return clubList;
    }

    //Create a ClubRepresentation with Clubs and without FootballersList
    public static List<ClubRepresentation> getRepresentationList1(List<Club> clubList) {
//        List<Club> clubList = getClubList1();
        List<ClubRepresentation> clubRepresentations = new LinkedList<>();

        Collections.addAll(clubRepresentations,
                new ClubRepresentation(clubList.get(0)),
                new ClubRepresentation(clubList.get(0)),
                new ClubRepresentation(clubList.get(0)),
                new ClubRepresentation(clubList.get(0)),
                new ClubRepresentation(clubList.get(1)),
                new ClubRepresentation(clubList.get(1)),
                new ClubRepresentation(clubList.get(2)),
                new ClubRepresentation(clubList.get(2)),
                new ClubRepresentation(clubList.get(2)),
                new ClubRepresentation(clubList.get(2)),
                new ClubRepresentation(clubList.get(3)),
                new ClubRepresentation(clubList.get(3)));

        return clubRepresentations;
    }


    public static List<Footballer> getFootballerList1() {
        List<Footballer> footballerList = new LinkedList<>();

        Collections.addAll(footballerList,
                new Footballer("111111", "Lewandowski", 150),
                new Footballer("222222", "Neymar", 160),
                new Footballer("333333", "Ikar", 160),
                new Footballer("444444", "Messi", 170),
                new Footballer("555555", "Ronaldo", 170),
                new Footballer("666666", "Zlatan", 180));

        return footballerList;
    }

    //Creating Footballers with sets of representations
    public static List<Footballer> getFootballerListWithRepresentations(List<Club> clubList) {

        //Creating example representationSets (only the size of it matters) --> save the ClubRepresentations and refer footballer to them
        //It should save with the join table

        //Save clubRepresentations


        Set<ClubRepresentation> clubRepresentationSet1 = Set.of(new ClubRepresentation(clubList.get(0)),
                new ClubRepresentation(clubList.get(0)),
                new ClubRepresentation(clubList.get(0)),
                new ClubRepresentation(clubList.get(0)));

        Set<ClubRepresentation> clubRepresentationSet2 = Set.of(new ClubRepresentation(clubList.get(0)),
                new ClubRepresentation(clubList.get(1)),
                new ClubRepresentation(clubList.get(1)));

        Set<ClubRepresentation> clubRepresentationSet3 = Set.of(new ClubRepresentation(clubList.get(0)),
                new ClubRepresentation(clubList.get(2)),
                new ClubRepresentation(clubList.get(2)),
                new ClubRepresentation(clubList.get(2)),
                new ClubRepresentation(clubList.get(2)));

        List<Footballer> footballerList = new LinkedList<>();
        Collections.addAll(footballerList,

                //Footballer doesn't have that field
                new Footballer("111111", "Lewandowski", 150, clubRepresentationSet1),
                new Footballer("222222", "Neymar", 160, clubRepresentationSet2),
                new Footballer("333333", "Ikar", 160, clubRepresentationSet3)
                );

        return footballerList;
    }

    //Create a list of Contracts (we need to import Clubs and Footballers)
    public static List<Contract> getContractList1(List<Club> clubList, List<Footballer> footballerList) {

        //Creating a list of Contracts
        List<Contract> contractList = new LinkedList<>();
        Collections.addAll(contractList,

                new Contract(clubList.get(0), footballerList.get(0),
                        Instant.now().minus(100, ChronoUnit.DAYS), Instant.now(), 10000),
                new Contract(clubList.get(1), footballerList.get(1),
                        Instant.now().minus(200, ChronoUnit.DAYS), Instant.now(), 20000),
                new Contract(clubList.get(2), footballerList.get(2),
                        Instant.now().minus(300, ChronoUnit.DAYS), Instant.now(), 30000));

        return contractList;
    }

    //We need ClubRepresentation list for host and guests, and leave the Scores list empty
    public static List<Match> getMatchList(List<ClubRepresentation> representationList) {

        //Create a list of matches
        // --> Club1 plays 4 matches as host, Club2 plays 2 as guest, Club3 plays 2 as guest
        List<Match> matchList = new LinkedList<>();
        Collections.addAll(matchList,
                new Match(representationList.get(0), representationList.get(4),
                        "Referee1", Instant.now(), Collections.emptyList()),
                new Match(representationList.get(1), representationList.get(5),
                        "Referee2", Instant.now(), Collections.emptyList()),
                new Match(representationList.get(2), representationList.get(6),
                        "Referee3", Instant.now(), Collections.emptyList()),

                new Match(representationList.get(3), representationList.get(7),
                        "Referee4", Instant.now(), Collections.emptyList())
        );
        return matchList;
    }

    //We need match and footballer to create Scores
    public static List<Score> getScoresList(List<Match> matchList, List<Footballer> footballerList) {

        //Create a list of Scores
        List<Score> scoreList = new LinkedList<>();
        Collections.addAll(scoreList,

                new Score(matchList.get(0), footballerList.get(0), 10),
                new Score(matchList.get(0), footballerList.get(0), 10),
                new Score(matchList.get(1), footballerList.get(1), 10),
                new Score(matchList.get(1), footballerList.get(1), 10),
                new Score(matchList.get(1), footballerList.get(1), 10),
                new Score(matchList.get(2), footballerList.get(2), 10)
        );

        return scoreList;
    }
}
