package com.lms.learnkonnet.dtos.requests.topic;

import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TopicRequestDto {
    private Long courseId;
    private Long order;
    private String name;
    private String desc;
    private Boolean isAnnounced ;
    private Timestamp startedAt;
    private Timestamp endedAt;
    private Status status;
}
