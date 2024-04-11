package com.lms.learnkonnet.dtos.requests.section;

import com.lms.learnkonnet.dtos.requests.relations.ExerciseSectionRequestDto;
import com.lms.learnkonnet.dtos.requests.relations.MaterialSectioRequestDto;
import com.lms.learnkonnet.models.enums.Status;
import com.lms.learnkonnet.models.relations.ExerciseSection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SectionRequestDto {
    private Long courseId;
    private String name;
    private String desc;
    private Long topicId;
    private Long order;
    private Timestamp startedAt;
    private Timestamp endedAt;
    private Status status;
    private Set<MaterialSectioRequestDto> materialSectionRequestDtos = new HashSet<>();
    private Set<ExerciseSectionRequestDto> excerciseSectionRequestDtos = new HashSet<>();
}
