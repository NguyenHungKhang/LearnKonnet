package com.lms.learnkonnet.dtos.responses.relations;

import com.lms.learnkonnet.dtos.responses.answer.AnswerResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.member.MemberBasicInfoResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberExerciseDetailResponseDto {
    private Long id;
    private ExerciseSumaryResponseDto exercise;
    private MemberBasicInfoResponseDto member;
    private Float score;

    private Set<AnswerResponseDto> answers = new HashSet<>();
}
