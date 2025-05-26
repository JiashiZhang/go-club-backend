package com.goclub.xian.pairing.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "bye")
public class Bye {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tournamentId;
    private Integer round;
    private Long playerId;
    private String remark;
}
