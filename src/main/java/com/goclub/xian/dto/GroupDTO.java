package com.goclub.xian.dto;

import lombok.Data;

@Data
public class GroupDTO {
    private String name;
    private Integer minLevel;
    private Integer maxLevel;
    private String remark;
}
