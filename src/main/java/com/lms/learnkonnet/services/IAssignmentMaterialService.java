package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.assignment.AssignmentRequestDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.assignment.material.AssignmentMaterialResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.schedule.ScheduleDetailResponseDto;

public interface IAssignmentMaterialService {
    PageResponse<AssignmentMaterialResponseDto> getPageableListByAssignment(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long assignmentId);
    AssignmentMaterialResponseDto getDetailById(Long id);
    AssignmentDetailResponseDto add(AssignmentRequestDto assignmentMaterial);
    AssignmentDetailResponseDto update(Long id, AssignmentRequestDto assignmentMaterial);
    Boolean softDelete(Long id);
    Boolean delete(Long id);
}
