package com.lms.learnkonnet.dtos.responses.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentResponseDto {
    private Long id;
    private Long postId;
    private String content;
    private Long parentId;
    private Set<CommentResponseDto> comments = new HashSet<>();
}
