package com.owczarczak.footballers.match;

import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.score.Score;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Builder
@Table(name = "match")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @OneToOne
    @JoinColumn(name = "host_representation_id")
    private ClubRepresentation host;
    @OneToOne
    @JoinColumn(name = "guest_representation_id")
    private ClubRepresentation guest;
    private String nameOfReferee;
    private Instant date;
    @OneToMany(mappedBy = "match")
    private List<Score> scores;
}
