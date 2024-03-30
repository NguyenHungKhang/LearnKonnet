package com.lms.learnkonnet.dtos.requests;

import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberRequestDto {
    private Long userId;
    private Long courseId;
    private float gpa;
    private MemberType type;
    private MemberStatus status;
}