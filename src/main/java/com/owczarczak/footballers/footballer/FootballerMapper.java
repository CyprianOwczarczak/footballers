package com.owczarczak.footballers.footballer;

import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface FootballerMapper {

    Footballer toEntity(FootballerDto source);

    FootballerDto toDto(Footballer source);

    List<FootballerDto> toDto(List<Footballer> source);
}
