package com.owczarczak.footballers.score;

import com.owczarczak.footballers.footballer.Footballer;
import com.owczarczak.footballers.match.Match;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "score")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "footballer_id")
    private Footballer footballer;

    @ManyToOne
    @JoinColumn(name = "match_id")
    private Match match;
    private int minuteScored;
}
