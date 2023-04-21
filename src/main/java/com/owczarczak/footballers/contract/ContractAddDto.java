package com.owczarczak.footballers.contract;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ContractAddDto {
    private Long id;
    private Long clubId;
    private Long footballerId;
    private LocalDate contractStart;
    private LocalDate contractEnd;
    private Integer salary;
}
