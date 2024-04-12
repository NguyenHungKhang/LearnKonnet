package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.excercise.ExerciseAccessWPasswordRequestDto;
import com.lms.learnkonnet.dtos.requests.excercise.ExerciseRequestDto;
import com.lms.learnkonnet.dtos.requests.schedule.ScheduleRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.PasswordAccessExerciseResponseDto;
import com.lms.learnkonnet.dtos.responses.schedule.ScheduleDetailResponseDto;

import java.util.List;

public interface IExerciseService {
    String getPasswordExercise(Long id, Long currentUserId);
    Boolean accessExcersiseWPassword(Long id, ExerciseAccessWPasswordRequestDto exerciseAccessWPassword, Long currentUserId);
    PageResponse<ExerciseSumaryResponseDto> getPageableListByCourse(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long currentUserId, Long courseId);
    PageResponse<ExerciseSumaryResponseDto> getPageableListBySection(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long sectionId);
    List<ExerciseSumaryResponseDto> getAllByCourse(Long courseId, Long currentUserId);
    List<ExerciseSumaryResponseDto> getAllBySection(Long sectionId);
    ExerciseDetailResponseDto getDetailById(Long id, Long currentUserId);
    ExerciseSumaryResponseDto getSumaryById(Long id, Long currentUserId);
    ExerciseInfoResponseDto getInfoById(Long id, Long currentUserId);
    ExerciseInfoResponseDto add(ExerciseRequestDto exercise, Long currentUserId);
    ExerciseInfoResponseDto update(Long id, ExerciseRequestDto exercise, Long currentUserId);
    Boolean softDelete(Long id, Long currentUserId);
    Boolean delete(Long id, Long currentUserId);
}
