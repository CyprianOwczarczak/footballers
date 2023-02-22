package com.owczarczak.footballers.club;

import lombok.*;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Builder
@Table(name = "clubs")
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
}
