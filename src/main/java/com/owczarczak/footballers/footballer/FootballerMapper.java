package com.owczarczak.footballers.footballer;

import org.mapstruct.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface FootballerMapper {

    Footballer toEntity(FootballerDto source);

    FootballerDto toDto(Footballer source);

    FootballerDto toDto(Optional<Footballer> source);


    List<FootballerDto> toDto(List<Footballer> source);
}
