package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.topic.TopicRequestDto;
import com.lms.learnkonnet.dtos.requests.user.CreateUserRequestDto;
import com.lms.learnkonnet.dtos.requests.user.UpdateUserRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserOwnerResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserSumaryResponseDto;
import com.lms.learnkonnet.models.Course;

import java.util.List;

public interface ITopicService {
    PageResponse<TopicDetailResponseDto> getPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId);
    List<TopicDetailResponseDto> getAll(Long courseId);
    TopicDetailResponseDto getById(Long id);
    TopicDetailResponseDto add(TopicRequestDto topic, Long currentMemberId);
    TopicDetailResponseDto update(Long id, TopicRequestDto topic, Long currentMemberId);
    Boolean softDelete(Long id);
    Boolean delete(Long id);
}
