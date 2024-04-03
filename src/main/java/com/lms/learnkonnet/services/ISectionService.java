package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.section.SectionRequestDto;
import com.lms.learnkonnet.dtos.requests.topic.TopicRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.section.SectionDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;

import java.util.List;

public interface ISectionService {
    PageResponse<SectionDetailResponseDto> getPageableListByTopic(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long topicId);
    PageResponse<SectionDetailResponseDto> getPageableListByCourse(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId);
    List<SectionDetailResponseDto> getAllByTopic(Long topicId);
    List<SectionDetailResponseDto> getAllByCourse(Long courseId);
    SectionDetailResponseDto getById(Long id);
    SectionDetailResponseDto add(SectionRequestDto section);
    SectionDetailResponseDto update(Long id, SectionRequestDto section);
    Boolean softDelete(Long id);
    Boolean delete(Long id);
}
