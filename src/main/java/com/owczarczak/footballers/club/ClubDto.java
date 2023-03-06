package com.owczarczak.footballers.club;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ClubDto {
    private int id;
    private String name;
    private Instant created;
//    private List<Contract> contractList;
}
