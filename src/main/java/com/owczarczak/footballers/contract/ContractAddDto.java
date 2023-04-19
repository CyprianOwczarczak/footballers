package com.owczarczak.footballers.contract;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.footballer.Footballer;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
public class ContractAddDto {
    private int id;
    private int clubId;
    private int footballerId;
    private LocalDate contractStart;
    private LocalDate contractEnd;
    private int salary;
}
