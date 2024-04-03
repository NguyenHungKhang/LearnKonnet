package com.lms.learnkonnet.dtos.responses.relations;

import com.lms.learnkonnet.dtos.responses.excercise.ExerciseSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.member.MemberBasicInfoResponseDto;
import com.lms.learnkonnet.models.Template;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberExerciseResponseDto {
    private Long id;
    private ExerciseSumaryResponseDto exercise;
    private MemberBasicInfoResponseDto member;
    private Template template;
    private Float score;
}
