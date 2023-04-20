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

    private ClubDto club;

    private List<FootballerDto> footballerList;
}
