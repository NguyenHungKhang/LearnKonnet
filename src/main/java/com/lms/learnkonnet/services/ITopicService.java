package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.topic.TopicRequestDto;
import com.lms.learnkonnet.dtos.requests.user.CreateUserRequestDto;
import com.lms.learnkonnet.dtos.requests.user.UpdateUserRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.topic.TopicBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserOwnerResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserSumaryResponseDto;
import com.lms.learnkonnet.models.Course;

import java.util.List;

public interface ITopicService {
    PageResponse<TopicDetailResponseDto> getPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long currentUserId, Long courseId);
    List<TopicDetailResponseDto> getAll(Long courseId, Long currentUserId);
    TopicDetailResponseDto getById(Long id, Long currentUserId);
    TopicBasicInfoResponseDto add(TopicRequestDto topic, Long currentUserId);
    TopicBasicInfoResponseDto update(Long id, TopicRequestDto topic, Long currentUserId);
    Boolean softDelete(Long id, Long currentUserId);
    Boolean delete(Long id, Long currentUserId);
}
