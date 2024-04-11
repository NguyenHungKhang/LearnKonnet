package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.comment.CommentRequestDto;
import com.lms.learnkonnet.dtos.requests.member.MemberRequestDto;
import com.lms.learnkonnet.dtos.responses.comment.CommentResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.member.MemberBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.member.MemberDetailResponseDto;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;

import java.util.List;

public interface IMemberService {
    PageResponse<MemberBasicInfoResponseDto> getInfoPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId);
    PageResponse<MemberDetailResponseDto> getDetailPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId);
    PageResponse<MemberBasicInfoResponseDto> getStudentInfoPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId);
    PageResponse<MemberBasicInfoResponseDto> getStudentInfoPageableListByMemberAndType(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long currentUserId, Long courseId, MemberStatus status, MemberType type);
    PageResponse<MemberDetailResponseDto> getStudentDetailPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId);

    List<MemberDetailResponseDto> getAll(Long postId);
    List<MemberDetailResponseDto> getAllStudent(Long postId);
    List<MemberBasicInfoResponseDto> getAllInfo(Long postId);
    List<MemberBasicInfoResponseDto> getAllStudentInfo(Long postId, Long currentUserId);
    MemberBasicInfoResponseDto getInfoById(Long id, Long currentUserId);
    MemberDetailResponseDto getDetailById(Long id, Long currentUserId);
    MemberDetailResponseDto add(MemberRequestDto member, Long currentUserId);
    MemberDetailResponseDto update(Long id, MemberRequestDto member, Long currentUserId);
    Boolean softDelete(Long id, Long currentUserId);
    Boolean delete(Long id, Long currentUserId);
}
