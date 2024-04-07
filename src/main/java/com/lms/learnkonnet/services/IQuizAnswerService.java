package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.answer.assignment.AssignmentAnswerRequestDto;
import com.lms.learnkonnet.dtos.requests.answer.quiz.QuizAnswerRequestDto;
import com.lms.learnkonnet.dtos.responses.answer.assignment.AssignmentAnswerResponseDto;
import com.lms.learnkonnet.dtos.responses.answer.quiz.QuizAnswerResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;

import java.util.List;

public interface IQuizAnswerService {
    PageResponse<QuizAnswerResponseDto> getPageableListByAnswer(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long answerId);
    List<QuizAnswerResponseDto> getAllByAnswer(Long answerId);
    QuizAnswerResponseDto getById(Long id);
    QuizAnswerResponseDto add(QuizAnswerRequestDto quizAnswer, Long currentMemberId);
    QuizAnswerResponseDto update(Long id, QuizAnswerRequestDto quizAnswer, Long currentMemberId);
    Boolean softDelete(Long id, Long currentMemberId);
    Boolean delete(Long id);
}
