package com.goclub.xian.dto;

import lombok.Data;
import java.util.List;

@Data
public class PageResponseDTO<T> {
    private List<T> content;
    private long totalElements;
    private int totalPages;
}