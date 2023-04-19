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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static java.time.Instant.now;
import static java.util.Collections.addAll;
import static java.util.Collections.emptyList;

public class TestDataFactory {
    private ClubRepository clubRepository;

    @Autowired
    private ClubRepresentationRepository representationRepository;

    public static List<Footballer> getFootballerList() {
        List<Footballer> footballerList = new LinkedList<>();

        addAll(footballerList,
                new Footballer("111111", "Lewandowski", 150),
                new Footballer("222222", "Neymar", 160),
                new Footballer("333333", "Ikar", 160),
                new Footballer("444444", "Messi", 170),
                new Footballer("555555", "Ronaldo", 170),
                new Footballer("666666", "Zlatan", 180));

        return footballerList;
    }

    public static List<Club> getClubList() {
        List<Club> clubList = new LinkedList<>();

        //Creating dates of founding the clubs
        LocalDate localDate1 = LocalDate.of(2015, 1, 5);
        LocalDate localDate2 = LocalDate.of(2016, 2, 10);
        LocalDate localDate3 = LocalDate.of(2017, 3, 15);
        LocalDate localDate4 = LocalDate.of(2018, 4, 20);
        LocalDate localDate5 = LocalDate.of(2019, 5, 25);

        //Adding Clubs to the list
        addAll(clubList,
                new Club("Barcelona", localDate1),
                new Club("Lech", localDate2),
                new Club("Real", localDate3),
                new Club("Manchester", localDate4),
                new Club("Bayern", localDate5));

        return clubList;
    }

    //Create a ClubRepresentation with Clubs and without FootballersList
    public static List<ClubRepresentation> getRepresentationList1(List<Club> clubList) {
//        List<Club> clubList = getClubList1();
        List<ClubRepresentation> clubRepresentations = new LinkedList<>();

        addAll(clubRepresentations,
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

    //Creating Footballers with sets of representations
    public static List<Footballer> getFootballersWithRepresentations(List<Club> clubList) {

        //Creating example representationSets without FootballersList
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
        addAll(footballerList,
                new Footballer("111111", "Lewandowski", 150, clubRepresentationSet1),
                new Footballer("222222", "Neymar", 160, clubRepresentationSet2),
                new Footballer("333333", "Ikar", 160, clubRepresentationSet3));

        return footballerList;
    }

    //We need ClubRepresentation list for host and guests, and leave the Scores list empty
    public static List<Match> getMatchList(List<ClubRepresentation> representationList) {

        //Create a list of matches
        // --> Club1 plays 4 matches as host, Club2 plays 2 as guest, Club3 plays 2 as guest
        List<Match> matchList = new LinkedList<>();
        addAll(matchList,
                new Match(representationList.get(0), representationList.get(4),
                        "Referee1", LocalDateTime.now(), emptyList()),
                new Match(representationList.get(1), representationList.get(5),
                        "Referee2", LocalDateTime.now(), emptyList()),
                new Match(representationList.get(2), representationList.get(6),
                        "Referee3", LocalDateTime.now(), emptyList()),
                new Match(representationList.get(3), representationList.get(7),
                        "Referee4", LocalDateTime.now(), emptyList())
        );
        return matchList;
    }

    //Create a list of Contracts (we need to import Clubs and Footballers)
    public static List<Contract> getContractList1(List<Club> clubList, List<Footballer> footballerList) {

        //Creating contractStart and contractEnd dates (All Contracts last 3 years
        LocalDate startContract1 = LocalDate.of(2015, 1, 5);
        LocalDate startContract2 = LocalDate.of(2016, 2, 10);
        LocalDate startContract3 = LocalDate.of(2017, 3, 15);

        LocalDate endContract1 = LocalDate.of(2018, 1, 5);
        LocalDate endContract2 = LocalDate.of(2019, 2, 10);
        LocalDate endContract3 = LocalDate.of(2020, 3, 15);

        //Creating a list of Contracts
        List<Contract> contractList = new LinkedList<>();
        addAll(contractList,

                new Contract(clubList.get(0), footballerList.get(0), startContract1,
                        endContract1, 10000),
                new Contract(clubList.get(1), footballerList.get(1), startContract2,
                        endContract2, 20000),
                new Contract(clubList.get(2), footballerList.get(2), startContract3,
                        endContract3, 30000));

        return contractList;
    }

    //We need match and footballer to create Scores
    public static List<Score> getScoresList(List<Match> matchList, List<Footballer> footballerList) {

        //Create a list of Scores
        List<Score> scoreList = new LinkedList<>();
        addAll(scoreList,

                new Score(matchList.get(0), footballerList.get(0), 10),
                new Score(matchList.get(0), footballerList.get(0), 20),
                new Score(matchList.get(1), footballerList.get(1), 30),
                new Score(matchList.get(1), footballerList.get(1), 40),
                new Score(matchList.get(1), footballerList.get(1), 50),
                new Score(matchList.get(2), footballerList.get(2), 60)
        );
        return scoreList;
    }

    //Creating Footballers with sets of representations
    public static List<Footballer> getFootballersWithRepresentationsList(List<Club> clubList) {

        //Creating footballersList
        List<Footballer> footballerList1 = getFootballerList();

        Footballer footballer1 = new Footballer();
        Footballer footballer2 = new Footballer();
        List<Footballer> footballerList2 = List.of(footballer1, footballer2);

        //Creating example representationSets with FootballersList
        Set<ClubRepresentation> clubRepresentationSet1 = Set.of(new ClubRepresentation(clubList.get(0)),
                new ClubRepresentation(clubList.get(0), footballerList1),
                new ClubRepresentation(clubList.get(0), footballerList1),
                new ClubRepresentation(clubList.get(0), footballerList1));

        Set<ClubRepresentation> clubRepresentationSet2 = Set.of(new ClubRepresentation(clubList.get(0)),
                new ClubRepresentation(clubList.get(1), footballerList2),
                new ClubRepresentation(clubList.get(1), footballerList2));

        Set<ClubRepresentation> clubRepresentationSet3 = Set.of(new ClubRepresentation(clubList.get(0)),
                new ClubRepresentation(clubList.get(2), footballerList1),
                new ClubRepresentation(clubList.get(2), footballerList1),
                new ClubRepresentation(clubList.get(2), footballerList1),
                new ClubRepresentation(clubList.get(2), footballerList1));

        List<Footballer> footballerList = new LinkedList<>();
        addAll(footballerList,
                new Footballer("111111", "Lewandowski", 150, clubRepresentationSet1),
                new Footballer("222222", "Neymar", 160, clubRepresentationSet2),
                new Footballer("333333", "Ikar", 160, clubRepresentationSet3));

        return footballerList;
    }

    public static List<ClubRepresentation> getRepresentationsWithFootballersList(
            List<Club> clubList, List<Footballer> footballerList1, List<Footballer> footballerList2) {
        //Creating footballersLists
        List<ClubRepresentation> clubRepresentations = new LinkedList<>();

        addAll(clubRepresentations,
                new ClubRepresentation(clubList.get(0), footballerList1),
                new ClubRepresentation(clubList.get(0), footballerList1),
                new ClubRepresentation(clubList.get(0), footballerList1),
                new ClubRepresentation(clubList.get(0), footballerList1),
                new ClubRepresentation(clubList.get(1), footballerList2),
                new ClubRepresentation(clubList.get(1), footballerList2),
                new ClubRepresentation(clubList.get(2), footballerList1),
                new ClubRepresentation(clubList.get(2), footballerList1),
                new ClubRepresentation(clubList.get(2), footballerList1),
                new ClubRepresentation(clubList.get(2), footballerList1),
                new ClubRepresentation(clubList.get(3), footballerList2),
                new ClubRepresentation(clubList.get(3), footballerList2));

        return clubRepresentations;
    }
}
