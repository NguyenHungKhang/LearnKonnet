package com.lms.learnkonnet.dtos.requests;

import com.lms.learnkonnet.models.Quiz;
import com.lms.learnkonnet.models.enums.QuizQuestionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionRequestDto {
    private Long quizId;
    private Integer order;
    private Float score;
    private Integer level;
    private String content;
    private QuizQuestionType questionType;
}
