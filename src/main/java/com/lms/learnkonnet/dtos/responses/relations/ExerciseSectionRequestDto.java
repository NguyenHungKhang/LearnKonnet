package com.lms.learnkonnet.dtos.responses.relations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExerciseSectionRequestDto {
    private Long sectionId;
    private Long exerciseId;
    private Integer order;
}
