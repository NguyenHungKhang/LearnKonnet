package com.lms.learnkonnet.dtos.requests.question;

import com.lms.learnkonnet.dtos.requests.choice.ChoiceRequestDto;
import com.lms.learnkonnet.models.enums.QuizQuestionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FullQuestionRequestDto {
    private Long id;
    private Integer order;
    private Float weight;
    private Integer level;
    private String content;
    private Long quizId;
    private QuizQuestionType questionType;
    private Set<ChoiceRequestDto> choices = new HashSet<>();
}
