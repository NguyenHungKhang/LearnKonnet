package com.lms.learnkonnet.dtos.responses.relations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberExerciseRequestDto {
    private Long exerciseId;
    private Long memberId;
    private Float score;
}
