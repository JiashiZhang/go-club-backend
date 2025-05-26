package com.goclub.xian.tournament.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tournament")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private String startTime; // 或 LocalDateTime startTime
    private String endTime;   // 或 LocalDateTime endTime
    private String location;
    private String rules;
    private String status;
    private String createdBy;

    // getter & setter
}
