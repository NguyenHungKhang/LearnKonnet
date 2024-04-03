package com.lms.learnkonnet.dtos.responses.relations;

import com.lms.learnkonnet.dtos.responses.member.MemberBasicInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberPostResponseDto {
    private Long id;
    private Long postId;
    private MemberBasicInfoResponseDto member;
}
