package com.owczarczak.footballers.clubRepresentation;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClubRepresentationAddDto {
    private Long id;

    private Long clubId;

    private List<Long> footballersIdList;
}
