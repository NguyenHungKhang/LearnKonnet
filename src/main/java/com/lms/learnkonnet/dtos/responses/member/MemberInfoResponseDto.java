package com.lms.learnkonnet.dtos.responses.member;

import com.lms.learnkonnet.dtos.responses.user.UserSumaryResponseDto;
import com.lms.learnkonnet.models.enums.MemberType;

public class MemberInfoResponseDto {
    private Long id;
    private UserSumaryResponseDto user;
    private Long courseId;
    private MemberType type;
}
