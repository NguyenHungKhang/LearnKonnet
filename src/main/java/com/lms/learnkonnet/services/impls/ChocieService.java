package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.question.QuestionRequestDto;
import com.lms.learnkonnet.dtos.responses.choice.ChoiceDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.choice.ChoiceDetailForTeacherResponseDto;
import com.lms.learnkonnet.dtos.responses.question.QuestionDetailForTeacherResponseDto;
import com.lms.learnkonnet.services.IChoiceService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChocieService implements IChoiceService  {
    @Override
    public ChoiceDetailForStudentResponseDto getDetailByStudentAndId(Long id) {
        return null;
    }

    @Override
    public ChoiceDetailForTeacherResponseDto getDetailByTeacherAndId(Long id) {
        return null;
    }

    @Override
    public List<ChoiceDetailForStudentResponseDto> getDetailByStudentAndQuestion(Long questionId) {
        return null;
    }

    @Override
    public List<ChoiceDetailForTeacherResponseDto> getDetailByTeacherAndQuestion(Long questionId) {
        return null;
    }

    @Override
    public QuestionDetailForTeacherResponseDto add(QuestionRequestDto question) {
        return null;
    }

    @Override
    public QuestionDetailForTeacherResponseDto update(Long id, QuestionRequestDto question) {
        return null;
    }

    @Override
    public Boolean softDelete(Long id) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }
}
