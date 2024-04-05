package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.material.MaterialRequestDto;
import com.lms.learnkonnet.dtos.requests.schedule.ScheduleRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.material.MaterialDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.schedule.ScheduleDetailResponseDto;

import java.util.List;

public interface IMaterialService {
    PageResponse<MaterialDetailResponseDto> getPageableListByCourse(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId);
    PageResponse<MaterialDetailResponseDto> getPageableListBySection(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long sectionId);
    List<MaterialDetailResponseDto> getAllByCourse(Long courseId);
    List<MaterialDetailResponseDto> getAllBySection(Long sectionId);
    MaterialDetailResponseDto getById(Long id);
    MaterialDetailResponseDto add(MaterialRequestDto material, Long currentMemberId);
    MaterialDetailResponseDto update(Long id, MaterialRequestDto material, Long currentMemberId);
    Boolean softDelete(Long id);
    Boolean delete(Long id);
}
