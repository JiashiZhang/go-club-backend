package com.goclub.xian.dto;

import lombok.Data;

@Data
public class PageRequestDTO {
    private int page = 0;
    private int size = 10;
}