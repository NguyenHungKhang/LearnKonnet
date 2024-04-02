package com.lms.learnkonnet.dtos.responses.question;

import com.lms.learnkonnet.models.enums.QuizQuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FullQuestionRequestDto {
    private Integer order;
    private Float score;
    private Integer level;
    private String content;
    private QuizQuestionType questionType;
}
