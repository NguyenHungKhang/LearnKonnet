package com.lms.learnkonnet.dtos.responses.course;

import com.lms.learnkonnet.dtos.responses.user.UserSumaryResponseDto;
import com.lms.learnkonnet.models.enums.Status;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CourseDetailResponseDto {
    private Long id;
    private String slug;
    private UserSumaryResponseDto user;
    private String name;
    private String desc;
    private String cover;
    private String code;
    private Boolean isApproveStudents;
    private Boolean isPreventStudents;
    private Boolean isShowScore;
    private Boolean isStudentAllowPost;
    private Boolean isStudentAllowComment;
    private Timestamp startedAt;
    private Timestamp endedAt;
    private Status status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
