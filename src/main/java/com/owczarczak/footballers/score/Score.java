package com.owczarczak.footballers.score;

import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.match.Match;

import javax.persistence.*;

@Entity
@Table(name = "score")
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

    public Footballer getFootballer() {
        return footballer;
    }

    public void setFootballer(Footballer footballer) {
        this.footballer = footballer;
    }

    public int getMinuteScored() {
        return minuteScored;
    }

    public void setMinuteScored(int minuteScored) {
        this.minuteScored = minuteScored;
    }
}
