package com.goclub.xian.game.models;

import jakarta.persistence.Entity;
import jakarta.persistence.*;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "game")
public class Game {
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
    @Column(name = "result")
    private String result;   // "A_WIN", "B_WIN", "DRAW", "FORFEIT"
    @Column(name = "a_rating_before")
    private Integer aRatingBefore;
    @Column(name = "b_rating_before")
    private Integer bRatingBefore;
    @Column(name = "a_rating_after")
    private Integer aRatingAfter;
    @Column(name = "b_rating_after")
    private Integer bRatingAfter;
    @Column(name = "play_time")
    private LocalDateTime playTime;
    @Column(name = "game_record")
    private String gameRecord;
    @Column(name = "remark")
    private String remark;
}
