package com.lms.learnkonnet.dtos.requests.quiz;

import com.lms.learnkonnet.dtos.requests.ChoiceRequestDto;
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
    private Integer order;
    private Float score;
    private Integer level;
    private String content;
    private QuizQuestionType questionType;
    private Set<ChoiceRequestDto> choiceRequestDtos = new HashSet<>();
}
