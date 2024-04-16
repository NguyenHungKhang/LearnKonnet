package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.assignment.AssignmentRequestDto;
import com.lms.learnkonnet.dtos.requests.assignment.material.AssignmentMaterialRequestDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.assignment.material.AssignmentMaterialResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.schedule.ScheduleDetailResponseDto;
import com.lms.learnkonnet.models.AssignmentMaterial;

import java.util.List;

public interface IAssignmentMaterialService {
    PageResponse<AssignmentMaterialResponseDto> getPageableListByAssignment(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long assignmentId);
    AssignmentMaterialResponseDto getDetailById(Long id);
    List<AssignmentDetailResponseDto> multiUpdate(List<AssignmentMaterialRequestDto> assignmentMaterial, Long currentUserId);
    AssignmentDetailResponseDto add(AssignmentMaterialRequestDto assignmentMaterial, Long currentMemberId);
    AssignmentDetailResponseDto update(Long id, AssignmentMaterialRequestDto assignmentMaterial, Long currentMemberId);
    Boolean softDelete(Long id, Long currentMemberId);
    Boolean delete(Long id);
}
