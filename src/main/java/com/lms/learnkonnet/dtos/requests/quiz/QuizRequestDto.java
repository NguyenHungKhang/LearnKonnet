package com.lms.learnkonnet.dtos.requests.quiz;

import com.lms.learnkonnet.models.Exercise;
import com.lms.learnkonnet.models.enums.QuizGradedType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class QuizRequestDto {
    private Long exerciseId;
    private Boolean isMixQuestion;
    private Boolean isMixAnswer;
    private Boolean isLimitAttempts;
    private Integer attempts;
    private QuizGradedType gradedType;
    private Boolean isLimitNumberOfQuestion ;
    private Boolean isQuestionLevelClassification;
    private Integer numberOfQuestion;
    private Integer numsOfLvl1;
    private Integer numsOfLvl2;
    private Integer numsOfLvl3;
    private Boolean isReviewed;
    private Boolean isShowScore;
    private Boolean isShowAnswer;

}
