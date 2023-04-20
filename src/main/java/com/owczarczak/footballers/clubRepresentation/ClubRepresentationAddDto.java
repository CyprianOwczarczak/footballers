package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.club.ClubDto;
import com.owczarczak.footballers.footballer.FootballerDto;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClubRepresentationAddDto {
    private int id;

    //Fixme change ClubDto to ClubId and remove FootballerDto List ?
    private ClubDto club;

    private List<FootballerDto> footballerList;
}
