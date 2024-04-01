package com.lms.learnkonnet.dtos.requests.quiz;

import com.lms.learnkonnet.dtos.requests.TemplateRequestDto;
import com.lms.learnkonnet.dtos.requests.relations.MemberPostRequestDto;
import com.lms.learnkonnet.models.enums.QuizGradedType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FullQuizRequestDto {
    private Long exerciseId;
    private String name;
    private Boolean isMixQuestion;
    private Boolean isMixAnswer;
    private Boolean isLimitTimesToDo;
    private Integer timestodo;
    private QuizGradedType gradedType;
    private Boolean isLimitNumberOfQuestion ;
    private Boolean isQuestionLevelClassification;
    private Boolean isMixWithExerciseCode;
    private Integer maxNumsOfExerciseCode;
    private Integer numsOfLvl1;
    private Integer numsOfLvl2;
    private Integer numsOfLvl3;

    //
    private Set<TemplateRequestDto> templateRequestDtos = new HashSet<>();
    private Set<FullQuestionRequestDto> fullQuestionRequestDtos = new HashSet<>();
}
