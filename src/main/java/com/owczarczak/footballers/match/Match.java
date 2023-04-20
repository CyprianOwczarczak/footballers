package com.owczarczak.footballers.match;

import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Builder
@Table(name = "match")
@Getter
@Setter
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "host_representation_id")
    private ClubRepresentation host;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JoinColumn(name = "guest_representation_id")
    private ClubRepresentation guest;

    private String nameOfReferee;

    private LocalDateTime date;

    public Match() {
    }

    public Match(int id, ClubRepresentation host, ClubRepresentation guest, String nameOfReferee, LocalDateTime date) {
        this.id = id;
        this.host = host;
        this.guest = guest;
        this.nameOfReferee = nameOfReferee;
        this.date = date;
    }

    public Match(ClubRepresentation host, ClubRepresentation guest, String nameOfReferee, LocalDateTime date) {
        this.host = host;
        this.guest = guest;
        this.nameOfReferee = nameOfReferee;
        this.date = date;
    }
}
