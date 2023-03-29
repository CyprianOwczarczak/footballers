package com.owczarczak.footballers.club;

import com.owczarczak.footballers.contract.Contract;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
