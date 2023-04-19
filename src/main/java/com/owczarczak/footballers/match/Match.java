package com.owczarczak.footballers.match;

import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.score.Score;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    private ClubRepresentation host;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "guest_representation_id")
    private ClubRepresentation guest;

    private String nameOfReferee;

    private LocalDateTime date;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "match_id")
    private List<Score> scores;

    public Match() {
    }

    public Match(int id, ClubRepresentation host, ClubRepresentation guest, String nameOfReferee, LocalDateTime date, List<Score> scores) {
        this.id = id;
        this.host = host;
        this.guest = guest;
        this.nameOfReferee = nameOfReferee;
        this.date = date;
        this.scores = scores;
    }

    public Match(ClubRepresentation host, ClubRepresentation guest, String nameOfReferee, LocalDateTime date, List<Score> scores) {
        this.host = host;
        this.guest = guest;
        this.nameOfReferee = nameOfReferee;
        this.date = date;
        this.scores = scores;
    }
}
