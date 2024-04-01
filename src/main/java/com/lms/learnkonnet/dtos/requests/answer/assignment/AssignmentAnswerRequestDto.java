package com.lms.learnkonnet.dtos.requests.answer.assignment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignmentAnswerRequestDto {
    private Long answerId;
    private Long fileId;
    private String content;
    private Integer order;
}
