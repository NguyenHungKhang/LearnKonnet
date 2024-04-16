package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.assignment.AssignmentRequestDto;
import com.lms.learnkonnet.dtos.requests.quiz.QuizRequestDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizSumaryResponseDto;

public interface IAssignmentService {
    AssignmentDetailResponseDto getDetailById(Long id);
    AssignmentSumaryResponseDto getSumaryById(Long id);
    AssignmentDetailResponseDto add(AssignmentRequestDto assignment, Long currentUserId);
    AssignmentDetailResponseDto update(Long id, AssignmentRequestDto assignment, Long currentUserId);
    Boolean softDelete(Long id, Long currentUserId);
    Boolean delete(Long id, Long currentUserId);
}
