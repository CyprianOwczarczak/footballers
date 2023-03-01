package com.owczarczak.footballers.club;

import com.owczarczak.footballers.contract.Contract;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Table(name = "club")
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

    public Club(String name, Instant created, List<Contract> contractList) {
        this.name = name;
        this.created = created;
        this.contractList = contractList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    public List<Contract> getContractList() {
        return contractList;
    }

    public void setContractList(List<Contract> contractList) {
        this.contractList = contractList;
    }
}
