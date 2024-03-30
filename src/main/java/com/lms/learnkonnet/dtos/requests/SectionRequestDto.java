package com.lms.learnkonnet.dtos.requests;

import com.lms.learnkonnet.models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SectionRequestDto {
    private Long courseId;
    private String name;
    private String desc;
    private Long topicId;
    private Long orderInTopic;
    private Timestamp startedAt;
    private Timestamp endedAt;
    private Status status;
}
