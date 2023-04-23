package com.owczarczak.footballers.footballer;

import com.owczarczak.footballers.clubRepresentation.ClubRepresentation;
import com.owczarczak.footballers.contract.Contract;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
    private Long id;
    private String pesel;
    private String name;
    private Integer height;

    @OneToMany(mappedBy = "footballer")
    private Set<Contract> contractList;

    @ManyToMany(mappedBy = "footballerList", cascade = CascadeType.REMOVE)
    private Set<ClubRepresentation> representationList;

    public Footballer(final String pesel, final String name, final Integer height) {
        this.pesel = pesel;
        this.name = name;
        this.height = height;
    }

    public Footballer(final String pesel, final String name, final Integer height, Set<ClubRepresentation> representationList) {
        this.pesel = pesel;
        this.name = name;
        this.height = height;
        this.representationList = representationList;
    }

    @Override
    public String toString() {
        return "Footballer{" +
                "id=" + id +
                ", pesel='" + pesel + '\'' +
                ", name='" + name + '\'' +
                ", height=" + height +
                '}';
    }
}
