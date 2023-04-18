package com.owczarczak.footballers.contract;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.footballer.Footballer;
import lombok.Builder;
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
import java.time.Instant;

@Builder
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

    public Contract(int id, Club club, Footballer footballer, Instant contractStart, Instant contractEnd, int salary) {
        this.id = id;
        this.club = club;
        this.footballer = footballer;
        this.contractStart = contractStart;
        this.contractEnd = contractEnd;
        this.salary = salary;
    }
}
