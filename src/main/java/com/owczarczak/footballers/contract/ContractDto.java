package com.owczarczak.footballers.contract;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ContractDto {
    private Long id;
    private String clubName;
    private String footballerName;
    private LocalDate contractStart;
    private LocalDate contractEnd;
    private Integer salary;
}
