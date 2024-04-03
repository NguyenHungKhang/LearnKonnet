package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.post.PostRequestDto;
import com.lms.learnkonnet.dtos.requests.topic.TopicRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.post.PostResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;

import java.util.List;

public interface IPostService {
    PageResponse<PostResponseDto> getPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId);
    List<PostResponseDto> getAll(Long courseId);
    PostResponseDto getById(Long id);
    PostResponseDto add(PostRequestDto post);
    PostResponseDto update(Long id, PostRequestDto topic);
    Boolean softDelete(Long id);
    Boolean delete(Long id);
}
