package com.owczarczak.footballers.match;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class MatchDto {
    private int id;
    private String hostClubName;
    private String guestClubName;
    private String nameOfReferee;
    private Instant date;
}
