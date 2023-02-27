package com.owczarczak.footballers.footballer;

import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.contract.Contract;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Builder
@Entity
@Table(name = "footballer")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Footballer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String pesel;
    String name;
    int goals;
    int height;
    @OneToMany(mappedBy = "footballer")
    Set<Contract> contractList;
    @ManyToMany(mappedBy = "footballerList")
    Set<ClubRepresentation> representationList;

    public Footballer(final String pesel, final String name, final String club, final int goals, final int height) {
        this.pesel = pesel;
        this.name = name;
        this.goals = goals;
        this.height = height;
    }

    @Override
    public String toString() {
        return "Footballer{" +
                "id=" + id +
                ", pesel='" + pesel + '\'' +
                ", name='" + name + '\'' +
                ", goals=" + goals +
                ", height=" + height +
                '}';
    }
}
