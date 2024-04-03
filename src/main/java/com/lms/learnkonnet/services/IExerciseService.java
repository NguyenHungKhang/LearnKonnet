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
    PasswordAccessExerciseResponseDto getPasswordExercise(Long id);
    Boolean accessExcersiseWPassword(Long id, ExerciseAccessWPasswordRequestDto exerciseAccessWPassword);
    PageResponse<ExerciseSumaryResponseDto> getPageableListByCourse(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId);
    PageResponse<ExerciseSumaryResponseDto> getPageableListBySection(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long sectionId);
    List<ExerciseSumaryResponseDto> getAllByCourse(Long courseId);
    List<ExerciseSumaryResponseDto> getAllBySection(Long sectionId);
    ExerciseDetailResponseDto getDetailById(Long id);
    ExerciseSumaryResponseDto getSumaryById(Long id);
    ExerciseInfoResponseDto getInfoById(Long id);
    ExerciseInfoResponseDto add(ExerciseRequestDto exercise);
    ExerciseInfoResponseDto update(Long id, ExerciseRequestDto exercise);
    Boolean softDelete(Long id);
    Boolean delete(Long id);
}
