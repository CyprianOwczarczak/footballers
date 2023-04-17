package com.owczarczak.footballers.contract;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.footballer.Footballer;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ContractAddDto {
    private int id;
    private Club club;
    private Footballer footballer;
    private Instant contractStart;
    private Instant contractEnd;
    private int salary;
}
