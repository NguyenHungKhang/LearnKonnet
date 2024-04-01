package com.lms.learnkonnet.dtos.requests.relations;

import com.lms.learnkonnet.models.Exercise;
import com.lms.learnkonnet.models.Section;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
