package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.comment.CommentRequestDto;
import com.lms.learnkonnet.dtos.requests.member.MemberRequestDto;
import com.lms.learnkonnet.dtos.responses.comment.CommentResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.member.MemberBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.member.MemberDetailResponseDto;

import java.util.List;

public interface IMemberService {
    PageResponse<MemberBasicInfoResponseDto> getInfoPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId);
    PageResponse<MemberDetailResponseDto> getDetailPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId);
    PageResponse<MemberBasicInfoResponseDto> getStudentInfoPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId);
    PageResponse<MemberDetailResponseDto> getStudentDetailPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId);
    List<MemberDetailResponseDto> getAll(Long postId);

    List<MemberDetailResponseDto> getAllStudent(Long postId);

    List<MemberBasicInfoResponseDto> getAllInfo(Long postId);

    List<MemberBasicInfoResponseDto> getAllStudentInfo(Long postId);
    MemberBasicInfoResponseDto getInfoById(Long id);
    MemberDetailResponseDto getDetailById(Long id);
    CommentResponseDto add(CommentRequestDto comment);
    CommentResponseDto update(Long id, CommentRequestDto comment);
    Boolean softDelete(Long id);
    Boolean delete(Long id);
}
