package com.lms.learnkonnet.dtos.responses.answer.quiz;

import com.lms.learnkonnet.dtos.responses.choice.ChoiceDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.question.QuestionDetailForStudentResponseDto;
import com.lms.learnkonnet.models.Choice;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuizAnswerResponseDto {
    private Long answerId;
    private ChoiceDetailForStudentResponseDto choice;
    private QuestionDetailForStudentResponseDto question;
    private Integer order;
    private String content;
    private Boolean caseSensitivity;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
