package com.owczarczak.footballers;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class TestDataFactory {

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
        LocalDateTime localDateTime1 = LocalDateTime.of(2015, 1, 5, 1, 10, 0);
        LocalDateTime localDateTime2 = LocalDateTime.of(2016, 2, 10, 2, 20, 0);
        LocalDateTime localDateTime3 = LocalDateTime.of(2017, 3, 15, 3, 30, 0);
        LocalDateTime localDateTime4 = LocalDateTime.of(2018, 4, 20, 4, 40, 0);
        LocalDateTime localDateTime5 = LocalDateTime.of(2019, 5, 25, 5, 50, 0);

        //Adding Clubs to the list
        Collections.addAll(clubList,
                new Club("Barcelona", localDateTime1.toInstant(ZoneOffset.UTC)),
                new Club("Lech", localDateTime2.toInstant(ZoneOffset.UTC)),
                new Club("Real", localDateTime3.toInstant(ZoneOffset.UTC)),
                new Club("Manchester", localDateTime4.toInstant(ZoneOffset.UTC)),
                new Club("Bayern", localDateTime5.toInstant(ZoneOffset.UTC)));

        return clubList;
    }

    //Create a ClubRepresentation without FootballersList
    private static List<ClubRepresentation> getRepresentationList1() {
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
}
