package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.schedule.ScheduleRequestDto;
import com.lms.learnkonnet.dtos.requests.section.SectionRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.schedule.ScheduleDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.section.SectionDetailResponseDto;

import java.util.List;

public interface IScheduleService {
    PageResponse<ScheduleDetailResponseDto> getPageableListByCourse(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId);
    PageResponse<ScheduleDetailResponseDto> getPageableListByUser(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long userId);
    List<ScheduleDetailResponseDto> getAllByCourse(Long courseId);
    List<ScheduleDetailResponseDto> getAllByUser(Long userid);
    ScheduleDetailResponseDto getById(Long id);
    ScheduleDetailResponseDto add(ScheduleRequestDto schedule);
    ScheduleDetailResponseDto update(Long id, ScheduleRequestDto schedule);
    Boolean softDelete(Long id);
    Boolean delete(Long id);
}
