package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.answer.AnswerRequestDto;
import com.lms.learnkonnet.dtos.requests.material.MaterialRequestDto;
import com.lms.learnkonnet.dtos.responses.answer.AnswerResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.material.MaterialDetailResponseDto;

import java.util.List;

public interface IAnswerService {
    PageResponse<AnswerResponseDto> getPageableListByExercise(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long exerciseId);
    PageResponse<AnswerResponseDto> getPageableListByStudent(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long memberId);
    AnswerResponseDto getById(Long id);
    AnswerResponseDto add(AnswerRequestDto answer, Long currentMemberId);
    AnswerResponseDto update(Long id, AnswerRequestDto answer, Long currentMemberId);
    Boolean softDelete(Long id, Long currentMemberId);
    Boolean delete(Long id);
}
