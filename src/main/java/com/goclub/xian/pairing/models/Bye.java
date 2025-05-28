package com.goclub.xian.pairing.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bye")
public class Bye {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long tournamentId;

    @Column(nullable = false)
    private Integer round;

    @Column
    private Long groupId; // 可为空

    @Column(nullable = false)
    private Long userId;

    private String remark;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
