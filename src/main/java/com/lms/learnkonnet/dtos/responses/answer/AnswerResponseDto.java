package com.lms.learnkonnet.dtos.responses.answer;

import com.lms.learnkonnet.dtos.responses.answer.assignment.AssignmentAnswerResponseDto;
import com.lms.learnkonnet.dtos.responses.answer.quiz.QuizAnswerResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnswerResponseDto {
    private Long id;
    private Long memberExerciseId;
    private String answerPattern;
    private String questionPattern;
    private Float score;
    private Set<AssignmentAnswerResponseDto> assignmentAnswers = new HashSet<>();
    private Set<QuizAnswerResponseDto> quizAnswers = new HashSet<>();
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
