package com.lms.learnkonnet.dtos.responses.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CommentRequestDto {
    private Long postId;
    private String content;
    private Long parentId;
}
