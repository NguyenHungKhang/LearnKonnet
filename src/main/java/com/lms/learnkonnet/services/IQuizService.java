package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.excercise.ExerciseAccessWPasswordRequestDto;
import com.lms.learnkonnet.dtos.requests.excercise.ExerciseRequestDto;
import com.lms.learnkonnet.dtos.requests.quiz.QuizRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.PasswordAccessExerciseResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizDetailForTeacherResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizSumaryResponseDto;

import java.util.List;

public interface IQuizService {
    QuizDetailForStudentResponseDto getDetailByStudentAndId(Long id, Long currentUserId);
    QuizDetailForTeacherResponseDto getDetailByTeacherAndId(Long id, Long currentUserId);
    QuizSumaryResponseDto getSumaryById(Long id, Long currentUserId);
    QuizSumaryResponseDto add(QuizRequestDto quiz, Long currentUserId);
    QuizSumaryResponseDto update(Long id, QuizRequestDto quiz, Long currentUserId);
    Boolean softDelete(Long id, Long currentUserId);
    Boolean delete(Long id, Long currentUserId);
}
