package com.owczarczak.footballers.clubRepresentation;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClubRepresentationAddDto {
    private int id;

    private int clubId;

    private List<Integer> footballersIdList;
}
