package com.lms.learnkonnet.dtos.requests.course;

import com.lms.learnkonnet.models.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
public class CourseRequestDto {
    private Long userId;
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
}
