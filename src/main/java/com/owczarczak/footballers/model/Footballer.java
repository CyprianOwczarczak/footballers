package com.owczarczak.footballers.model;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Slf4j
@AllArgsConstructor
@RequiredArgsConstructor
public class Footballer implements Serializable {
    @Id
    private int id;
    private final String pesel;
    private String name;
    private String club;
    private int goals;
    private int height;

    public Footballer(final String pesel, final String name, final String club, final int height) {
        this.pesel = pesel;
        this.name = name;
        this.club = club;
        this.height = height;

        log.info("Added a footballer: " + name);
    }

    @Override
    public final boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Footballer that = (Footballer) o;
        return Objects.equals(pesel, that.pesel);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(pesel);
    }

    //todo switch off the 'final' prompt, and turn off check for some test classes
    public String toString() {
        return "Pesel: " + pesel + " name: " + name + ", football club: " + club + ", number of goals scored: " + goals + ", height: " + height;
    }
}
