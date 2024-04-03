package com.lms.learnkonnet.dtos.responses.relations;

import com.lms.learnkonnet.dtos.responses.excercise.ExerciseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseSumaryResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExerciseSectionResponseDto {
    private Long sectionId;
    private ExerciseSumaryResponseDto exercise;
    private Integer order;
}
