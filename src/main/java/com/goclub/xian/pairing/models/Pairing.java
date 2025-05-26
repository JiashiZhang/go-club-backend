package com.goclub.xian.pairing.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "pairing")
public class Pairing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tournament_id")
    private Long tournamentId;
    @Column(name = "round")
    private Integer round;
    @Column(name = "table_no")
    private Integer tableNo;
    @Column(name = "player_a_id")
    private Long playerAId;
    @Column(name = "player_b_id")
    private Long playerBId;

    @Enumerated(EnumType.STRING)
    private PairingResult result = PairingResult.NOT_PLAYED;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
