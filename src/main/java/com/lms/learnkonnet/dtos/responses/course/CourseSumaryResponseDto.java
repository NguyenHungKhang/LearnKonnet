package com.lms.learnkonnet.dtos.responses.course;

import com.lms.learnkonnet.dtos.responses.user.UserSumaryResponseDto;
import com.lms.learnkonnet.models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseSumaryResponseDto {
    private Long id;
    private String slug;
    private UserSumaryResponseDto user;
    private String name;
    private String desc;
    private String cover;
    private String code;
    private Timestamp startedAt;
    private Timestamp endedAt;
    private Status status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
