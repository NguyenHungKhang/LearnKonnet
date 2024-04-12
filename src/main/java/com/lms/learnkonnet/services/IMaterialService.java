package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.material.MaterialRequestDto;
import com.lms.learnkonnet.dtos.requests.schedule.ScheduleRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.material.MaterialDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.schedule.ScheduleDetailResponseDto;

import java.util.List;

public interface IMaterialService {
    PageResponse<MaterialDetailResponseDto> getPageableListByCourse(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId,  Long currentUserId);
    PageResponse<MaterialDetailResponseDto> getPageableListBySection(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long sectionId,  Long currentUserId);
    List<MaterialDetailResponseDto> getAllByCourse(Long courseId, Long currentUserId);
    List<MaterialDetailResponseDto> getAllBySection(Long sectionId);
    MaterialDetailResponseDto getById(Long id, Long currentUserId);
    MaterialDetailResponseDto add(MaterialRequestDto material, Long currentUserId);
    MaterialDetailResponseDto update(Long id, MaterialRequestDto material, Long currentUserId);
    Boolean softDelete(Long id, Long currentUserId);
    Boolean delete(Long id, Long currentUserId);
}
