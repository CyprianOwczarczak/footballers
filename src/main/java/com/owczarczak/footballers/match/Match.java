package com.owczarczak.footballers.match;

import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.score.Score;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "host_representation_id")
    ClubRepresentation host;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "guest_representation_id")
    ClubRepresentation guest;

    String nameOfReferee;

    Instant date;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
    @JoinColumn(name = "match_id")
    List<Score> scores;

    public Match() {
    }

    public Match(ClubRepresentation host, ClubRepresentation guest, String nameOfReferee, Instant date, List<Score> scores) {
        this.host = host;
        this.guest = guest;
        this.nameOfReferee = nameOfReferee;
        this.date = date;
        this.scores = scores;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ClubRepresentation getHost() {
        return host;
    }

    public void setHost(ClubRepresentation host) {
        this.host = host;
    }

    public ClubRepresentation getGuest() {
        return guest;
    }

    public void setGuest(ClubRepresentation guest) {
        this.guest = guest;
    }

    public String getNameOfReferee() {
        return nameOfReferee;
    }

    public void setNameOfReferee(String nameOfReferee) {
        this.nameOfReferee = nameOfReferee;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public List<Score> getScores() {
        return scores;
    }

    public void setScores(List<Score> scores) {
        this.scores = scores;
    }
}
