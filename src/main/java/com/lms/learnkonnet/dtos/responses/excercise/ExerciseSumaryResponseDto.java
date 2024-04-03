package com.lms.learnkonnet.dtos.responses.excercise;

import com.lms.learnkonnet.dtos.responses.quiz.QuizSumaryResponseDto;
import com.lms.learnkonnet.models.enums.ExerciseType;
import com.lms.learnkonnet.models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExerciseSumaryResponseDto {
    private Long id;
    private String slug;
    private Long courseId;
    private String name;
    private String desc;
    private ExerciseType exerciseType;
    private Timestamp startedAt;
    private Timestamp endedAt;
    private Status status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private QuizSumaryResponseDto quiz;
}
