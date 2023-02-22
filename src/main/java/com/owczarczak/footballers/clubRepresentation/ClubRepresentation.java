package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.footballer.Footballer;
import lombok.*;

import javax.persistence.*;
import java.util.List;

//@Entity
@Builder
@Table(name = "club_representations")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ClubRepresentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int match;
    private List<Footballer> footballerList;
    private Club club;
}
