package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.comment.CommentRequestDto;
import com.lms.learnkonnet.dtos.requests.post.PostRequestDto;
import com.lms.learnkonnet.dtos.responses.comment.CommentResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.post.PostResponseDto;

import java.util.List;

public interface ICommentService {
    PageResponse<CommentResponseDto> getPageableList(String sortField, String sortDir, int pageNum, int pageSize, Long postId);
    List<CommentResponseDto> getAll(Long postId);
    CommentResponseDto getById(Long id);
    CommentResponseDto add(CommentRequestDto comment, Long currentMemberId);
    CommentResponseDto update(Long id, CommentRequestDto comment, Long currentMemberId);
    Boolean softDelete(Long id, Long currentMemberId);
    Boolean delete(Long id);
}
