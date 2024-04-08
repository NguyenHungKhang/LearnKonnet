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
    Long id;
    String slug;
    UserSumaryResponseDto user;
    String name;
    String desc;
    String cover;
    String code;
    Boolean isApproveStudents;
    Boolean isPreventStudents;
    Boolean isShowScore;
    Boolean isStudentAllowPost;
    Boolean isStudentAllowComment;
    Timestamp startedAt;
    Timestamp endedAt;
    Status status;
    Timestamp createdAt;
    Timestamp modifiedAt;
}
