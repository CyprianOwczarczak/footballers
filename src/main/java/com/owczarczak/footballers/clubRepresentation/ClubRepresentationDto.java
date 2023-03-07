package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.footballer.Footballer;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
public class ClubRepresentationDto {
    int id;
    String clubName;
    List<Footballer> footballerList; //?
}
