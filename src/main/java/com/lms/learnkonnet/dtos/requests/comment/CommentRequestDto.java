package com.lms.learnkonnet.dtos.requests.comment;

import com.lms.learnkonnet.models.Comment;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Post;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
