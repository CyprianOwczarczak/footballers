package com.owczarczak.footballers.footballer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Builder
@Entity
@Table(name = "footballers")
@Getter
@Setter
@AllArgsConstructor
public class Footballer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String pesel;
    String name;
    String club;
    int goals;
    int height;

    public Footballer(final String pesel, final String name, final String club, final int height) {
        this.pesel = pesel;
        this.name = name;
        this.club = club;
        this.height = height;
    }

    public Footballer() {
    }

    @Override
    public String toString() {
        return "Footballer{" +
                "id=" + id +
                ", pesel='" + pesel + '\'' +
                ", name='" + name + '\'' +
                ", club='" + club + '\'' +
                ", goals=" + goals +
                ", height=" + height +
                '}';
    }
}
