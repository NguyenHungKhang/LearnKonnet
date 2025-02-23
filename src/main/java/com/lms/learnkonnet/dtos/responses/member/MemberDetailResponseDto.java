package com.lms.learnkonnet.dtos.responses.member;

import com.lms.learnkonnet.dtos.responses.user.UserSumaryResponseDto;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberDetailResponseDto {
    private Long id;
    private UserSumaryResponseDto user;
    private Long courseId;
    private float gpa;
    private MemberType type;
    private MemberStatus status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}