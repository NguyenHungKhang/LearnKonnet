package com.lms.learnkonnet.dtos.requests;

import com.lms.learnkonnet.dtos.requests.quiz.FullQuestionRequestDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignmentAnswerRequestDto {
    private Long answerId;
    private Long fileId;
    private String content;
    private Integer order;
}
