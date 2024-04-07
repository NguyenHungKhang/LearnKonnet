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
import com.lms.learnkonnet.dtos.responses.quiz.QuizSumaryResponseDto;

import java.util.List;

public interface IQuizService {
    QuizDetailForStudentResponseDto getDetailByStudentAndId(Long id);
    QuizDetailForStudentResponseDto getDetailByTeacherAndId(Long id);
    QuizSumaryResponseDto getSumaryById(Long id);
    QuizSumaryResponseDto add(QuizRequestDto quiz, Long currentMemberId);
    QuizSumaryResponseDto update(Long id, QuizRequestDto quiz, Long currentMemberId);
    Boolean softDelete(Long id, Long currentMemberId);
    Boolean delete(Long id);
}
