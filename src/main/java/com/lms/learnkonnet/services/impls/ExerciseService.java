package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.excercise.ExerciseAccessWPasswordRequestDto;
import com.lms.learnkonnet.dtos.requests.excercise.ExerciseRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.PasswordAccessExerciseResponseDto;
import com.lms.learnkonnet.dtos.responses.material.MaterialDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.repositories.ICourseRepository;
import com.lms.learnkonnet.repositories.IExerciseRepository;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.repositories.ITopicRepository;
import com.lms.learnkonnet.services.IExerciseService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExerciseService implements IExerciseService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IExerciseRepository exerciseRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public PasswordAccessExerciseResponseDto getPasswordExercise(Long id) {
        return exerciseRepository.getPasswordExercise(id);
    }

    @Override
    public Boolean accessExcersiseWPassword(Long id, ExerciseAccessWPasswordRequestDto exerciseAccessWPassword) {
        String password = exerciseAccessWPassword.getPassword();
        String exercisePassword = exerciseRepository.findPasswordById(id);
        return  exercisePassword != null && exercisePassword.equals(password);
    }

    @Override
    public PageResponse<ExerciseSumaryResponseDto> getPageableListByCourse(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Exercise> exercisesPage = exerciseRepository.findByCourseIdAndNameContaining(courseId, keyword, pageable);
        List<ExerciseSumaryResponseDto> exercisesDtoPage = modelMapperUtil.mapList(exercisesPage.getContent(), ExerciseSumaryResponseDto.class);

        return new PageResponse<>(
                exercisesDtoPage,
                exercisesPage.getNumber(),
                exercisesPage.getSize(),
                exercisesPage.getTotalElements(),
                exercisesPage.getTotalPages(),
                exercisesPage.isLast()
        );
    }

    @Override
    public PageResponse<ExerciseSumaryResponseDto> getPageableListBySection(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long sectionId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Exercise> exercisesPage = exerciseRepository.findAllByExerciseSections_SectionIdAndNameContaining(sectionId, keyword, pageable);
        List<ExerciseSumaryResponseDto> exercisesDtoPage = modelMapperUtil.mapList(exercisesPage.getContent(), ExerciseSumaryResponseDto.class);

        return new PageResponse<>(
                exercisesDtoPage,
                exercisesPage.getNumber(),
                exercisesPage.getSize(),
                exercisesPage.getTotalElements(),
                exercisesPage.getTotalPages(),
                exercisesPage.isLast()
        );
    }

    @Override
    public List<ExerciseSumaryResponseDto> getAllByCourse(Long courseId) {
        List<Exercise> exercises = exerciseRepository.findAllByCourseId(courseId);
        return modelMapperUtil.mapList(exercises, ExerciseSumaryResponseDto.class);
    }

    @Override
    public List<ExerciseSumaryResponseDto> getAllBySection(Long sectionId) {
        List<Exercise> exercises = exerciseRepository.findAllByExerciseSections_SectionId(sectionId);
        return modelMapperUtil.mapList(exercises, ExerciseSumaryResponseDto.class);
    }

    @Override
    public ExerciseDetailResponseDto getDetailById(Long id) {
        Exercise existExercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", id));
        return modelMapperUtil.mapOne(existExercise, ExerciseDetailResponseDto.class);
    }

    @Override
    public ExerciseSumaryResponseDto getSumaryById(Long id) {
        Exercise existExercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", id));
        return modelMapperUtil.mapOne(existExercise, ExerciseSumaryResponseDto.class);
    }

    @Override
    public ExerciseInfoResponseDto getInfoById(Long id) {
        Exercise existExercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", id));
        return modelMapperUtil.mapOne(existExercise, ExerciseInfoResponseDto.class);
    }

    @Override
    public ExerciseInfoResponseDto add(ExerciseRequestDto exercise, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Course course = courseRepository.findById(exercise.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourseId()));

        Exercise newExercise = modelMapperUtil.mapOne(exercise, Exercise.class);
        newExercise.setCreatedByMember(currentMember);
        newExercise.setCourse(course);
        Exercise savedExercise = exerciseRepository.save(newExercise);
        return modelMapperUtil.mapOne(savedExercise, ExerciseInfoResponseDto.class);
    }

    @Override
    public ExerciseInfoResponseDto update(Long id, ExerciseRequestDto exercise, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Exercise existExercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("exercise", "Id", id));

        existExercise.setName(exercise.getName());
        existExercise.setDesc(exercise.getDesc());
        existExercise.setExerciseType(exercise.getExerciseType());
        existExercise.setIsHasPassword(exercise.getIsHasPassword());
        existExercise.setPassword(exercise.getPassword());
        existExercise.setIsGraded(exercise.getIsGraded());
        existExercise.setFactor(exercise.getFactor());
        existExercise.setIsReviewed(exercise.getIsReviewed());
        existExercise.setIsShowScore(exercise.getIsShowScore());
        existExercise.setIsShowAnswer(exercise.getIsShowAnswer());
        existExercise.setDuration(exercise.getDuration());
        existExercise.setStartedAt(exercise.getStartedAt());
        existExercise.setEndedAt(exercise.getEndedAt());
        existExercise.setStatus(exercise.getStatus());
        existExercise.setUpdatedByMember(currentMember);

        Exercise savedExercise = exerciseRepository.save(existExercise);
        return modelMapperUtil.mapOne(savedExercise, ExerciseInfoResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Exercise existExercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", id));

        existExercise.setIsDeleted(!existExercise.getIsDeleted());
        existExercise.setUpdatedByMember(currentMember);
        Exercise savedExercise = exerciseRepository.save(existExercise);
        return savedExercise.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id) {
        Exercise existExercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", id));
        exerciseRepository.delete(existExercise);
        return true;
    }
}
