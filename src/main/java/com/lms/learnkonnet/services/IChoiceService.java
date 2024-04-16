package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.choice.ChoiceRequestDto;
import com.lms.learnkonnet.dtos.requests.question.QuestionRequestDto;
import com.lms.learnkonnet.dtos.responses.choice.ChoiceDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.choice.ChoiceDetailForTeacherResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.question.QuestionDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.question.QuestionDetailForTeacherResponseDto;

import java.util.List;

public interface IChoiceService {
    ChoiceDetailForStudentResponseDto getDetailByStudentAndId(Long id);
    ChoiceDetailForTeacherResponseDto getDetailByTeacherAndId(Long id);
    List<ChoiceDetailForStudentResponseDto> getDetailByStudentAndQuestion(Long questionId, Long currentUserId);
    List<ChoiceDetailForTeacherResponseDto> getDetailByTeacherAndQuestion(Long questionId, Long currentUserId);
    List<ChoiceDetailForTeacherResponseDto> updateMulti(List<ChoiceRequestDto> choices);
    ChoiceDetailForTeacherResponseDto add(ChoiceRequestDto choice);
    ChoiceDetailForTeacherResponseDto update(Long id, ChoiceRequestDto choice);
    Boolean softDelete(Long id);
    Boolean delete(Long id);
}
