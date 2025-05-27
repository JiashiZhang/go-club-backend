package com.goclub.xian.tournament.models;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tournament_group")
public class TournamentGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    private Tournament tournament;

    private String name; // 组别名（如“5级升2级”）
    private Integer minLevel; // 最低级/段
    private Integer maxLevel; // 最高级/段
    private String remark;
}
