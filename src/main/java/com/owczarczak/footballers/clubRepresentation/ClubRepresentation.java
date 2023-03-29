package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.footballer.Footballer;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "club_representation")
@Getter
@Setter
public class ClubRepresentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToMany
    @JoinTable(name = "club_representation_footballer",
            joinColumns = @JoinColumn(name = "club_representation_id"),
            inverseJoinColumns = @JoinColumn(name = "footballer_id"))
    List<Footballer> footballerList;
}
