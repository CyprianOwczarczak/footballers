package com.owczarczak.footballers.club;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

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
    int id;
    @Column(name = "club_name")
    String clubName;
    @Column(name = "date_of_creation", columnDefinition = "DATE")
    Date dateOfCreation;
}
