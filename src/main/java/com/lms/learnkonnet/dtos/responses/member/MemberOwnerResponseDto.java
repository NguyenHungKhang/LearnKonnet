package com.lms.learnkonnet.dtos.responses.member;

import com.lms.learnkonnet.dtos.responses.user.UserSumaryResponseDto;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberOwnerResponseDto {
    private Long id;
    private UserSumaryResponseDto user;
    private Long courseId;
    private float gpa;
    private MemberType type;
    private MemberStatus status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}