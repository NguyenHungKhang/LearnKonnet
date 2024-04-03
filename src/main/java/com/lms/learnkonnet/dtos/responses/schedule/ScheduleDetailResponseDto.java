package com.lms.learnkonnet.dtos.responses.schedule;

import com.lms.learnkonnet.models.enums.ClassType;
import com.lms.learnkonnet.models.enums.ScheduleRepeatType;
import com.lms.learnkonnet.models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScheduleDetailResponseDto {
    private Long id;
    private String slug;
    private Long courseId;
    private String title;
    private String desc;
    private String link;
    private ScheduleRepeatType repeatType;
    private ClassType type;
    private Integer repeatDay;
    private Status status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
