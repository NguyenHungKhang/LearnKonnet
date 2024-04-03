package com.lms.learnkonnet.dtos.responses.member;

import com.lms.learnkonnet.dtos.responses.user.UserSumaryResponseDto;
import com.lms.learnkonnet.models.enums.MemberType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberBasicInfoResponseDto {
    private Long id;
    private UserSumaryResponseDto user;
    private Long courseId;
    private MemberType type;
}
