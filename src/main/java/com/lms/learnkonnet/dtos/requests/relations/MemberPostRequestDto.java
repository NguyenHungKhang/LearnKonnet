package com.lms.learnkonnet.dtos.requests.relations;

import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Post;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberPostRequestDto {
    private Long memberId;
    private Long postId;
}
