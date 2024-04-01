package com.lms.learnkonnet.dtos.requests.material;

import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.File;
import com.lms.learnkonnet.models.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaterialRequestDto {
    private Long courseId;
    private Long fileId;
    private String name;
    private String desc;
    private Boolean isAnnounced ;
    private Timestamp startedAt;
    private Timestamp endedAt;
    private Status status;
}
