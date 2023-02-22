package com.owczarczak.footballers.Match;

import com.owczarczak.footballers.club.Club;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

//@Entity
@Builder
@Table(name = "matches")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Club hostTeam;
    private Club guestTeam;
    private String score;
    private String nameOfReferee;
    private Instant date;
}
