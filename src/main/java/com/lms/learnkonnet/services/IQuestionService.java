package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.assignment.AssignmentRequestDto;
import com.lms.learnkonnet.dtos.requests.question.QuestionRequestDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.question.QuestionDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.question.QuestionDetailForTeacherResponseDto;
import com.lms.learnkonnet.dtos.responses.schedule.ScheduleDetailResponseDto;

public interface IQuestionService {
    PageResponse<QuestionDetailForStudentResponseDto> getPageableListByStudent(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long quizId);
    PageResponse<QuestionDetailForTeacherResponseDto> getPageableListByTeacher(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long quizId);
    QuestionDetailForStudentResponseDto getDetailByStudentAndId(Long id);
    QuestionDetailForTeacherResponseDto getDetailByTeacherAndId(Long id);
    QuestionDetailForTeacherResponseDto add(QuestionRequestDto question);
    QuestionDetailForTeacherResponseDto update(Long id, QuestionRequestDto question);
    Boolean softDelete(Long id);
    Boolean delete(Long id);
}
