package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.quiz.QuizRequestDto;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.material.MaterialDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizSumaryResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.repositories.IExerciseRepository;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.repositories.IQuizRepository;
import com.lms.learnkonnet.services.IQuizService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuizService implements IQuizService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IExerciseRepository exerciseRepository;
    @Autowired
    private IQuizRepository quizRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public QuizDetailForStudentResponseDto getDetailByStudentAndId(Long id) {
        Quiz existQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));
        return modelMapperUtil.mapOne(existQuiz, QuizDetailForStudentResponseDto.class);
    }

    @Override
    public QuizDetailForStudentResponseDto getDetailByTeacherAndId(Long id) {
        Quiz existQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));
        return modelMapperUtil.mapOne(existQuiz, QuizDetailForStudentResponseDto.class);
    }

    @Override
    public QuizSumaryResponseDto getSumaryById(Long id) {
        Quiz existQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));
        return modelMapperUtil.mapOne(existQuiz, QuizSumaryResponseDto.class);
    }

    @Override
    public QuizSumaryResponseDto add(QuizRequestDto quiz, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Exercise exercise = exerciseRepository.findById(quiz.getExerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", quiz.getExerciseId()));

        Quiz newQuiz = modelMapperUtil.mapOne(quiz, Quiz.class);
        newQuiz.setExercise(exercise);
        newQuiz.setCreatedByMember(currentMember);
        Quiz savedQuiz = quizRepository.save(newQuiz);
        return modelMapperUtil.mapOne(savedQuiz, QuizSumaryResponseDto.class);
    }

    @Override
    public QuizSumaryResponseDto update(Long id, QuizRequestDto quiz, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Quiz existQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));

        existQuiz.setIsMixQuestion(quiz.getIsMixQuestion());
        existQuiz.setIsMixAnswer(quiz.getIsMixAnswer());
        existQuiz.setIsLimitTimesToDo(quiz.getIsLimitTimesToDo());
        existQuiz.setTimesToDo(quiz.getTimesToDo());
        existQuiz.setGradedType(quiz.getGradedType());
        existQuiz.setIsLimitNumberOfQuestion(quiz.getIsLimitNumberOfQuestion());
        existQuiz.setIsQuestionLevelClassification(quiz.getIsQuestionLevelClassification());
        existQuiz.setIsMixWithExerciseCode(quiz.getIsMixWithExerciseCode());
        existQuiz.setMaxNumsOfExerciseCode(quiz.getMaxNumsOfExerciseCode());
        existQuiz.setNumsOfLvl1(quiz.getNumsOfLvl1());
        existQuiz.setNumsOfLvl2(quiz.getNumsOfLvl2());
        existQuiz.setNumsOfLvl3(quiz.getNumsOfLvl3());
        existQuiz.setUpdatedByMember(currentMember);

        Quiz savedQuiz = quizRepository.save(existQuiz);
        return modelMapperUtil.mapOne(savedQuiz, QuizSumaryResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Quiz existQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));

        existQuiz.setIsDeleted(!existQuiz.getIsDeleted());
        existQuiz.setUpdatedByMember(currentMember);
        Quiz savedQuiz = quizRepository.save(existQuiz);
        return savedQuiz.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id) {
        Quiz existQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));
        quizRepository.delete(existQuiz);
        return true;
    }
}
