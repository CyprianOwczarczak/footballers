package com.owczarczak.footballers.Match;

import com.owczarczak.footballers.club.Club;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

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
    @ManyToOne
    @JoinColumn(name = "host_representation_id")
    private Club hostRepresentationId;
    @ManyToOne
    @JoinColumn(name = "guest_representation_id")
    private Club guestRepresentationId;
    private String nameOfReferee;
    private Instant date;
}
