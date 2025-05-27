package com.goclub.xian.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class GameHistoryDTO {
    private Long id;
    private String whiteName;    // 含ID: "张三 (1234)"
    private String blackName;    // 含ID: "李四 (5678)"
    private String date;         // "2025-01-11"
    private String tournament;   // 比赛名
    private Integer round;       // 轮次
    private Integer handicap;    // 让子
    private Integer komi;        // 贴目
    private String result;       // "W+5.5" / "B+R" / ...
}
