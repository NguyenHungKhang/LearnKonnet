package com.lms.learnkonnet.dtos.responses.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PageResponse<T> {
    private List<T> content;
    private Integer page;
    private Integer size;
    private Long totalElemnts;
    private Integer totalPages;
    private Boolean last;
}
