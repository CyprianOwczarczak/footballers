package com.owczarczak.footballers.contract;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.footballer.Footballer;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "contract")
@Getter
@Setter
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "club_id")
    private Club club;

    @OneToOne
    @JoinColumn(name = "footballer_id")
    private Footballer footballer;

    private Instant contractStart;

    private Instant contractEnd;

    private int salary;

    public Contract() {
    }

    public Contract(Club club, Footballer footballer, Instant contractStart, Instant contractEnd, int salary) {
        this.club = club;
        this.footballer = footballer;
        this.contractStart = contractStart;
        this.contractEnd = contractEnd;
        this.salary = salary;
    }
}
