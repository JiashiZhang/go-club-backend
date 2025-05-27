package com.goclub.xian.registration.models;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "registration", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "tournament_id"}))
public class Registration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "tournament_id", nullable = false)
    private Long tournamentId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "register_time")
    private LocalDateTime registerTime;

    private String remark;

    @Column(name = "sign_in_time")
    private LocalDateTime signInTime;

    private String status;

    @Column(name = "level_code")
    private Integer levelCode;
}
