package com.owczarczak.footballers.clubRepresentation;

import com.owczarczak.footballers.club.Club;
import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Table(name = "club_representation")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class ClubRepresentation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;
}
