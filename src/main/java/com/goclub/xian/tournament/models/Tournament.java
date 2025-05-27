package com.goclub.xian.tournament.models;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "tournament")
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // 主键也可以加，习惯上写
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "location")
    private String location;

    @Column(name = "rules")
    private String rules;

    @Column(name = "status")
    private String status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "province_limit")
    private String provinceLimit;

    @Column(name = "city_limit")
    private String cityLimit;

    // getter & setter
    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

}
