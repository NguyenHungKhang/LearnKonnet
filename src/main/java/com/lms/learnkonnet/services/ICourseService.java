package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.comment.CommentRequestDto;
import com.lms.learnkonnet.dtos.requests.course.CourseRequestDto;
import com.lms.learnkonnet.dtos.responses.comment.CommentResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.course.CourseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.course.CourseSumaryResponseDto;

import java.util.List;

public interface ICourseService {
    PageResponse<CourseSumaryResponseDto> getAllPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize);
    PageResponse<CourseSumaryResponseDto> getAllPageableListByUser(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long userId);
    PageResponse<CourseSumaryResponseDto> getAllPageableListByOwner(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long userId);
    List<CourseSumaryResponseDto> getAll(Long userId);
    CourseSumaryResponseDto getSumaryById(Long id);
    CourseDetailResponseDto getById(Long id, Long currentUserId);
    CourseDetailResponseDto add(CourseRequestDto course, Long currentUserId);
    CourseDetailResponseDto update(Long id, CourseRequestDto course, Long currentUserId);
    Boolean softDelete(Long id, Long currentUserId);
    Boolean delete(Long id, Long currentUserId);
}
