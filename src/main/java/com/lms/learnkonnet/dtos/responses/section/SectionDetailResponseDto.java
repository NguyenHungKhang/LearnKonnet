package com.lms.learnkonnet.dtos.responses.section;

import com.lms.learnkonnet.dtos.responses.relations.ExerciseSectionResponseDto;
import com.lms.learnkonnet.dtos.responses.relations.MaterialSectioResponseDto;
import com.lms.learnkonnet.models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SectionDetailResponseDto {
    private Long id;
    private String slug;
    private Long courseId;
    private String name;
    private String desc;
    private Long topicId;
    private Long order;
    private Timestamp startedAt;
    private Timestamp endedAt;
    private Status status;
    private Set<MaterialSectioResponseDto> materials = new HashSet<>();
    private Set<ExerciseSectionResponseDto> exercises = new HashSet<>();
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
