package com.lms.learnkonnet.dtos.responses.excercise.log;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExerciseLogResponseDto {
    private Long id;
    private Long exerciseId;
    private String content;
}
