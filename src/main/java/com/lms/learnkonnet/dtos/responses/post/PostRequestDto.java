package com.lms.learnkonnet.dtos.responses.post;

import com.lms.learnkonnet.dtos.responses.relations.MemberPostRequestDto;
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
public class PostRequestDto {
    private Long courseId;
    private String content;
    private String image;
    private PostType postType;
    private Timestamp postAt;
    private Set<MemberPostRequestDto> memberPostRequestDtos = new HashSet<>();
}
