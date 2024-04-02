package com.lms.learnkonnet.dtos.responses.question;

import com.lms.learnkonnet.dtos.responses.choice.ChoiceDetailForTeacherResponseDto;
import com.lms.learnkonnet.dtos.responses.section.SectionDetailResponseDto;
import com.lms.learnkonnet.models.enums.QuizQuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuestionDetailForTeacherResponseDto {
    private Long id;
    private Long quizId;
    private Integer order;
    private Float score;
    private Integer level;
    private String content;
    private QuizQuestionType questionType;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Set<ChoiceDetailForTeacherResponseDto> choiceDetailForTeacherResponseDtos = new HashSet<>();
}
