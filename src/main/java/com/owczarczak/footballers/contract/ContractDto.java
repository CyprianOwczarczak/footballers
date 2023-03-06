package com.owczarczak.footballers.contract;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ContractDto {
    private int id;
    private String clubName;
    private String footballerName;
    private Instant contractStart;
    private Instant contractEnd;
    private int salary;
}
