package com.lms.learnkonnet.dtos.responses.excercise;

import com.lms.learnkonnet.dtos.responses.quiz.QuizDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizDetailForTeacherResponseDto;
import com.lms.learnkonnet.models.enums.ExerciseType;
import com.lms.learnkonnet.models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExerciseDetailResponseDto {
    private Long id;
    private String slug;
    private Long courseId;
    private String name;
    private String desc;
    private ExerciseType exerciseType;
    private Boolean isHasPassword ;
    private Boolean isGraded;
    private Float factor;
    private Boolean isReviewed;
    private Boolean isShowScore;
    private Boolean isShowAnswer;
    private Integer duration;
    private Timestamp startedAt;
    private Timestamp endedAt;
    private Status status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    // question
    private Set<QuizDetailForStudentResponseDto> quizDetailForStudentResponseDtos = new HashSet<>();
    private Set<QuizDetailForTeacherResponseDto> quizDetailForTeacherResponseDtos = new HashSet<>();
}
