package com.owczarczak.footballers.match;

import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.score.Score;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Builder
@Table(name = "match")
@Getter
@Setter
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "host_representation_id")
    ClubRepresentation host;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "guest_representation_id")
    ClubRepresentation guest;

    String nameOfReferee;

    Instant date;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "match_id")
    List<Score> scores;

    public Match() {
    }

    public Match(int id, ClubRepresentation host, ClubRepresentation guest, String nameOfReferee, Instant date, List<Score> scores) {
        this.id = id;
        this.host = host;
        this.guest = guest;
        this.nameOfReferee = nameOfReferee;
        this.date = date;
        this.scores = scores;
    }

    public Match(ClubRepresentation host, ClubRepresentation guest, String nameOfReferee, Instant date, List<Score> scores) {
        this.host = host;
        this.guest = guest;
        this.nameOfReferee = nameOfReferee;
        this.date = date;
        this.scores = scores;
    }
}
