package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.section.SectionRequestDto;
import com.lms.learnkonnet.dtos.requests.topic.TopicRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.section.SectionDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;

import java.util.List;

public interface ISectionService {
    PageResponse<SectionDetailResponseDto> getPageableListByTopic(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long currentUserId, Long topicId);
    PageResponse<SectionDetailResponseDto> getPageableListByCourse(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId);
    List<SectionDetailResponseDto> getAllByTopic(Long topicId, Long currentUserId);
    List<SectionDetailResponseDto> getAllByCourse(Long courseId);
    SectionDetailResponseDto getById(Long id, Long currentUserId);
    SectionDetailResponseDto add(SectionRequestDto section, Long currentUserId);
    SectionDetailResponseDto update(Long id, SectionRequestDto section, Long currentUserId);
    Boolean softDelete(Long id, Long currentUserId);
    Boolean delete(Long id, Long currentUserId);
}
