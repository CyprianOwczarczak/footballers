package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.footballer.Footballer;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "club_representation")
public class ClubRepresentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @ManyToMany
//            (cascade = CascadeType.REMOVE)
    @JoinTable(name = "club_representation_footballer",
            joinColumns = @JoinColumn(name = "club_representation_id"),
            inverseJoinColumns = @JoinColumn(name = "footballer_id"))
    List<Footballer> footballerList;

    public ClubRepresentation() {
    }

    public ClubRepresentation(Club club) {
        this.club = club;
    }

    public ClubRepresentation(Club club, List<Footballer> footballerList) {
        this.club = club;
        this.footballerList = footballerList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public List<Footballer> getFootballerList() {
        return footballerList;
    }

    public void setFootballerList(List<Footballer> footballerList) {
        this.footballerList = footballerList;
    }
}
