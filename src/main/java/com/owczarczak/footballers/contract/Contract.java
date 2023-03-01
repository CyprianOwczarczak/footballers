package com.owczarczak.footballers.contract;

import com.owczarczak.footballers.club.Club;
import com.owczarczak.footballers.footballer.Footballer;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "contract")
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Footballer getFootballer() {
        return footballer;
    }

    public void setFootballer(Footballer footballer) {
        this.footballer = footballer;
    }

    public Instant getContractStart() {
        return contractStart;
    }

    public void setContractStart(Instant contractStart) {
        this.contractStart = contractStart;
    }

    public Instant getContractEnd() {
        return contractEnd;
    }

    public void setContractEnd(Instant contractEnd) {
        this.contractEnd = contractEnd;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
