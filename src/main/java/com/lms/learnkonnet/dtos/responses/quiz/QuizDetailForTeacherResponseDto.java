package com.lms.learnkonnet.dtos.responses.quiz;

import com.lms.learnkonnet.dtos.responses.question.QuestionDetailForTeacherResponseDto;
import com.lms.learnkonnet.models.enums.QuizGradedType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuizDetailForTeacherResponseDto {
    private Long id;
    private String slug;
    private Long exerciseId;
    private Boolean isMixQuestion;
    private Boolean isMixAnswer;
    private Boolean isLimitTimesToDo;
    private Integer timesTodo;
    private QuizGradedType gradedType;
    private Boolean isLimitNumberOfQuestion ;
    private Boolean isQuestionLevelClassification;
    private Boolean isMixWithExerciseCode;
    private Integer maxNumsOfExerciseCode;
    private Integer numsOfLvl1;
    private Integer numsOfLvl2;
    private Integer numsOfLvl3;
    private Set<QuestionDetailForTeacherResponseDto> questionDetailForTeacherResponseDtos = new HashSet<>();
}
