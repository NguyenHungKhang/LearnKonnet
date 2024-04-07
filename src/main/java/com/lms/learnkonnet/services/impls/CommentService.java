package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.comment.CommentRequestDto;
import com.lms.learnkonnet.dtos.responses.comment.CommentResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.topic.TopicBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.repositories.ICommentRepository;
import com.lms.learnkonnet.repositories.ICourseRepository;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.repositories.IPostRepository;
import com.lms.learnkonnet.services.ICommentService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService implements ICommentService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private ICommentRepository commentRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IPostRepository postRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public PageResponse<CommentResponseDto> getPageableList(String sortField, String sortDir, int pageNum, int pageSize, Long postId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Comment> commentsPage = commentRepository.findByPostId(postId, pageable);
        List<CommentResponseDto> commentsDtoPage = modelMapperUtil.mapList(commentsPage.getContent(), CommentResponseDto.class);

        return new PageResponse<>(
                commentsDtoPage,
                commentsPage.getNumber(),
                commentsPage.getSize(),
                commentsPage.getTotalElements(),
                commentsPage.getTotalPages(),
                commentsPage.isLast()
        );
    }

    @Override
    public List<CommentResponseDto> getAll(Long postId) {
        List<Comment> comments = commentRepository.findAllByPostId(postId);
        return modelMapperUtil.mapList(comments, CommentResponseDto.class);
    }

    @Override
    public CommentResponseDto getById(Long id) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "id", id));
        return modelMapperUtil.mapOne(comment, CommentResponseDto.class);
    }

    @Override
    public CommentResponseDto add(CommentRequestDto comment, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Post post = postRepository.findById(comment.getPostId())
                .orElseThrow(() -> new ResourceNotFoundException("Post", "Id", comment.getPostId()));

        Comment newComment = modelMapperUtil.mapOne(comment, Comment.class);
        if (comment.getParentId() != null) {
            Comment parrentComment = commentRepository.findById(comment.getParentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Parrent comment", "Id", comment.getParentId()));
            newComment.setParent(parrentComment);
        } else newComment.setParent(null);

        newComment.setCreatedByMember(currentMember);
        Comment savedComment = commentRepository.save(newComment);
        return modelMapperUtil.mapOne(savedComment, CommentResponseDto.class);
    }

    @Override
    public CommentResponseDto update(Long id, CommentRequestDto comment, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Comment existComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", id));

        existComment.setContent(comment.getContent());
        existComment.setUpdatedByMember(currentMember);

        Comment savedComment = commentRepository.save(existComment);
        return modelMapperUtil.mapOne(savedComment, CommentResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Comment existComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", id));

        existComment.setIsDeleted(!existComment.getIsDeleted());
        existComment.setUpdatedByMember(currentMember);
        Comment savedComment = commentRepository.save(existComment);
        return savedComment.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id) {
        Comment existComment = commentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment", "Id", id));
        commentRepository.delete(existComment);
        return true;
    }
}
