package com.owczarczak.footballers.model;

import javax.persistence.*;

@Entity
@Table(name = "footballers")
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

    public void updateFootballer(Footballer footballerToUpdate) {
        pesel = footballerToUpdate.pesel;
        name = footballerToUpdate.name;
        goals = footballerToUpdate.goals;
        height = footballerToUpdate.height;
    }

    public Footballer() {

    }

    public int getId() {
        return id;
    }

    public String getPesel() {
        return pesel;
    }

    public String getName() {
        return name;
    }

    public String getClub() {
        return club;
    }

    public int getGoals() {
        return goals;
    }

    public int getHeight() {
        return height;
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
