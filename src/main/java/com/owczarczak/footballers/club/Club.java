package com.owczarczak.footballers.club;

import com.owczarczak.footballers.contract.Contract;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Entity
@Builder
@Table(name = "club")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private Instant created;
    @OneToMany(mappedBy = "club")
    private List<Contract> contractList;
}
