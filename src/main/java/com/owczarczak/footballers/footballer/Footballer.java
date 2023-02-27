package com.owczarczak.footballers.footballer;

import lombok.*;

import javax.persistence.*;

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
//    String club;
    int goals;
    int height;

    public Footballer(final String pesel, final String name, final String club, final int goals, final int height) {
        this.pesel = pesel;
        this.name = name;
//        this.club = club;
        this.goals = goals;
        this.height = height;
    }

    @Override
    public String toString() {
        return "Footballer{" +
                "id=" + id +
                ", pesel='" + pesel + '\'' +
                ", name='" + name + '\'' +
//                ", club='" + club + '\'' +
                ", goals=" + goals +
                ", height=" + height +
                '}';
    }
}
