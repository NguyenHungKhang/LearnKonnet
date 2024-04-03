package com.lms.learnkonnet.dtos.requests.answer.quiz;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuizAnswerRequestDto {
    private Long answerId;
    private Long questionId;
    private Long choiceId;
    private Integer order;
    private String content;
    private Boolean caseSensitivity;
}
