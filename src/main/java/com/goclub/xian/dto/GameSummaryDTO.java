package com.goclub.xian.dto;

import lombok.Data;

@Data
public class GameSummaryDTO {
    private Long opponentId;
    private String opponentName;
    private int totalGames;
    private int wins;
    private int losses;
    private int draws;
}
