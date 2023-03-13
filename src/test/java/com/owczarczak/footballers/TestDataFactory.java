package com.owczarczak.footballers;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubRepository;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentationRepository;
import com.owczarczak.footballers.contract.Contract;
import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.score.Score;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.time.LocalDateTime.of;

public class TestDataFactory {

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    ClubRepresentationRepository representationRepository;


//    public static Club getClub1() {
//        return new Club("Barcelona", Instant.now());
//    }
//
//    public static Club getClub2() {
//        return new Club("Lech", Instant.now());
//    }
//
//    public static Club getClub3() {
//        return new Club("Real", Instant.now());
//    }
//
//    public static Club getClub4() {
//        return new Club("Manchester", Instant.now());
//    }
//
//    public static Club getClub5() {
//        return new Club("Bayern", Instant.now());
//    }

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
    public static List<ClubRepresentation> getRepresentationList1() {
        List<Club> clubList = getClubList1();
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

    //Create a list of Contracts (we need to import Clubs and Footballers)
    public static List<Contract> getContractList1(List<Club> clubList, List<Footballer> footballerList) {
//        List<Club> clubList = getClubList1();
//        List<Footballer> footballerList = getFootballerList1();

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


    public static List<Score> getScoreList() {

    }
}
