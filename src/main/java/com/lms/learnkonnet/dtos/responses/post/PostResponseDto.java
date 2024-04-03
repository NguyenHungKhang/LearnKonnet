package com.lms.learnkonnet.dtos.responses.post;

import com.lms.learnkonnet.dtos.responses.comment.CommentResponseDto;
import com.lms.learnkonnet.dtos.responses.relations.MemberPostResponseDto;
import com.lms.learnkonnet.models.enums.PostType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostResponseDto {
    private Long id;
    private Long courseId;
    private String content;
    private String image;
    private PostType postType;
    private Timestamp postAt;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Set<MemberPostResponseDto> members = new HashSet<>();
    private CommentResponseDto newestComment;
    private Set<CommentResponseDto> comments = new HashSet<>();
}
