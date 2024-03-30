package com.lms.learnkonnet.dtos.requests;

import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.enums.ClassType;
import com.lms.learnkonnet.models.enums.ScheduleRepeatType;
import com.lms.learnkonnet.models.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ScheduleRequestDto {
    private Long courseId;
    private String title;
    private String desc;
    private String link;
    private ScheduleRepeatType repeatType;
    private ClassType type;
    private Integer repeatDay;
    private Status status;
}
