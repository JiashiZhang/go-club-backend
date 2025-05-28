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

    @Column(nullable = false)
    private Long tournamentId;

    @Column(nullable = false)
    private Integer round;

    @Column(nullable = false)
    private Integer tableNo;

    @Column(nullable = false)
    private Long groupId;

    @Column(nullable = false)
    private Long playerAId;

    @Column(nullable = false)
    private Long playerBId;

    @Column(nullable = false, length = 16)
    private String result = "NOT_PLAYED"; // 建议用字符串或枚举

    private LocalDateTime startTime;
    private LocalDateTime endTime;
}
