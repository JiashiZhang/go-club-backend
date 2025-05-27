package com.goclub.xian.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EloHistoryDTO {
    private LocalDateTime playTime;
    private int elo;
}
