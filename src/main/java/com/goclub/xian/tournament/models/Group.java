package com.goclub.xian.tournament.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tournament_group")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tournament_id")
    private Long tournamentId;

    private String name; // 组别名（如“5级升2级”）
    private Integer minLevel; // 最低级/段
    private Integer maxLevel; // 最高级/段
    private String remark;
}
