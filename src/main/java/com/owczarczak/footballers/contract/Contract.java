package com.owczarczak.footballers.contract;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Builder
@Table(name = "contract")
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int clubId;
    private int footballerId;
    private Instant contractStart;
    private Instant contractEnd;
    private int salary;
}
