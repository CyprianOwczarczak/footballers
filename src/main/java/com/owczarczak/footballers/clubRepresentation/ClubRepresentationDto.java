package com.owczarczak.footballers.clubRepresentation;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClubRepresentationDto {
    int id;
    String clubName;
    int footballerListSize;

//    int id;
//
//    int clubId;
//    String clubName;
//    List<Footballer> footballerList;
//
//    int rivalClubId;
//    String rivalClubName;
//    List<Footballer> rivalFootballerList;
//
//    int matchId;
//    Instant matchDate;

    //todo add what match is it for (match id, date, which clubs played) -- > club name, club id, footballerList(id, etc)
    //Add more data to this Dto
}
