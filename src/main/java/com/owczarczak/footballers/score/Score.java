package com.owczarczak.footballers.score;

import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.match.Match;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "score")
@Getter
@Setter
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;

    @OneToOne
    @JoinColumn(name = "footballer_id")
    private Footballer footballer;

    private int minuteScored;

    public Score() {
    }

    public Score(Match match, Footballer footballer, int minuteScored) {
        this.match = match;
        this.footballer = footballer;
        this.minuteScored = minuteScored;
    }
}
