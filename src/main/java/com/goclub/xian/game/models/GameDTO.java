package com.goclub.xian.game.models;

import lombok.Data;

@Data
public class GameDTO {
    private Long tournamentId;
    private Integer round;
    private Integer tableNo;
    private Long playerAId;
    private Long playerBId;
    private String result; // "A_WIN", "B_WIN", "DRAW", "FORFEIT"
    private String gameRecordUrl;
    private String remark;
}
