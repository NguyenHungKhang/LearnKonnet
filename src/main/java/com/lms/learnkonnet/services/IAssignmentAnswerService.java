package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.answer.AnswerRequestDto;
import com.lms.learnkonnet.dtos.requests.answer.assignment.AssignmentAnswerRequestDto;
import com.lms.learnkonnet.dtos.responses.answer.AnswerResponseDto;
import com.lms.learnkonnet.dtos.responses.answer.assignment.AssignmentAnswerResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.material.MaterialDetailResponseDto;

import java.util.List;

public interface IAssignmentAnswerService {
    PageResponse<AssignmentAnswerResponseDto> getPageableListByAnswer(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long answerId);
    List<AssignmentAnswerResponseDto> getAllByAnswer(Long answerId);
    AssignmentAnswerResponseDto getById(Long id);
    AssignmentAnswerResponseDto add(AssignmentAnswerRequestDto assignmentAnswer, Long currentMemberId);
    AssignmentAnswerResponseDto update(Long id, AssignmentAnswerRequestDto assignmentAnswer, Long currentMemberId);
    Boolean softDelete(Long id, Long currentMemberId);
    Boolean delete(Long id);
}
