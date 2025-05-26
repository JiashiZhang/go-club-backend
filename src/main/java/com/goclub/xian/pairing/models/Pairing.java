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

    private Long tournamentId;
    private Integer round;
    private Integer tableNo;
    private Long playerAId;
    private Long playerBId;

    @Enumerated(EnumType.STRING)
    private PairingResult result = PairingResult.NOT_PLAYED;

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
