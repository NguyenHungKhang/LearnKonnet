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
    AssignmentDetailResponseDto add(AssignmentRequestDto assignment);
    AssignmentDetailResponseDto update(Long id, AssignmentRequestDto assignment);
    Boolean softDelete(Long id);
    Boolean delete(Long id);
}
