package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.footballer.Footballer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "club_representation")
@Getter
@Setter
@NoArgsConstructor
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

    public ClubRepresentation(Club club) {
        this.club = club;
    }

    public ClubRepresentation(Club club, List<Footballer> footballerList) {
        this.club = club;
        this.footballerList = footballerList;
    }
}
