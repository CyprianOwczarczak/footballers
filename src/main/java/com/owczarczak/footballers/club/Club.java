package com.owczarczak.footballers.club;

import com.owczarczak.footballers.contract.Contract;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "club")
@Getter
@Setter
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private Instant created;

    @OneToMany(mappedBy = "club")
    private List<Contract> contractList;

    public Club() {
    }

    public Club(String name, Instant created) {
        this.name = name;
        this.created = created;
    }

    public Club(String name, Instant created, List<Contract> contractList) {
        this.name = name;
        this.created = created;
        this.contractList = contractList;
    }
}
