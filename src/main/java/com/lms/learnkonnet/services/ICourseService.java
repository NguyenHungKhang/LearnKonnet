package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.comment.CommentRequestDto;
import com.lms.learnkonnet.dtos.responses.comment.CommentResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.course.CourseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.course.CourseSumaryResponseDto;

import java.util.List;

public interface ICourseService {
    PageResponse<CourseSumaryResponseDto> getPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize);
    PageResponse<CourseSumaryResponseDto> getPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long userId);
    List<CourseSumaryResponseDto> getAll(Long userId);
    CourseSumaryResponseDto getSumaryById(Long id);
    CourseDetailResponseDto getById(Long id);
    CommentResponseDto add(CommentRequestDto comment);
    CommentResponseDto update(Long id, CommentRequestDto comment);
    Boolean softDelete(Long id);
    Boolean delete(Long id);
}
