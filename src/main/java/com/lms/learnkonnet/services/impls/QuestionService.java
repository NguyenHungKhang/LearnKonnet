package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.question.QuestionRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.material.MaterialDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.question.QuestionDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.question.QuestionDetailForTeacherResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizSumaryResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.repositories.IExerciseRepository;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.repositories.IQuestionRepository;
import com.lms.learnkonnet.repositories.IQuizRepository;
import com.lms.learnkonnet.services.IQuestionService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService implements IQuestionService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IQuestionRepository questionRepository;
    @Autowired
    private IQuizRepository quizRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public PageResponse<QuestionDetailForStudentResponseDto> getPageableListByStudent(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long quizId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Question> questionsPage = questionRepository.findByQuiz_IdAndContentContaining(quizId, keyword, pageable);
        List<QuestionDetailForStudentResponseDto> questionsDtoPage = modelMapperUtil.mapList(questionsPage.getContent(), QuestionDetailForStudentResponseDto.class);

        return new PageResponse<>(
                questionsDtoPage,
                questionsPage.getNumber(),
                questionsPage.getSize(),
                questionsPage.getTotalElements(),
                questionsPage.getTotalPages(),
                questionsPage.isLast()
        );
    }

    @Override
    public PageResponse<QuestionDetailForTeacherResponseDto> getPageableListByTeacher(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long quizId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Question> questionsPage = questionRepository.findByQuiz_IdAndContentContaining(quizId, keyword, pageable);
        List<QuestionDetailForTeacherResponseDto> questionsDtoPage = modelMapperUtil.mapList(questionsPage.getContent(), QuestionDetailForTeacherResponseDto.class);

        return new PageResponse<>(
                questionsDtoPage,
                questionsPage.getNumber(),
                questionsPage.getSize(),
                questionsPage.getTotalElements(),
                questionsPage.getTotalPages(),
                questionsPage.isLast()
        );
    }

    @Override
    public QuestionDetailForStudentResponseDto getDetailByStudentAndId(Long id) {
        Question existQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "Id", id));
        return modelMapperUtil.mapOne(existQuestion, QuestionDetailForStudentResponseDto.class);
    }

    @Override
    public QuestionDetailForTeacherResponseDto getDetailByTeacherAndId(Long id) {
        Question existQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "Id", id));
        return modelMapperUtil.mapOne(existQuestion, QuestionDetailForTeacherResponseDto.class);
    }

    @Override
    public QuestionDetailForTeacherResponseDto add(QuestionRequestDto question, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Quiz quiz = quizRepository.findById(question.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", question.getQuizId()));

        Question newQuestion = modelMapperUtil.mapOne(question, Question.class);
        newQuestion.setQuiz(quiz);
        Question savedQuestion = questionRepository.save(newQuestion);
        return modelMapperUtil.mapOne(savedQuestion, QuestionDetailForTeacherResponseDto.class);
    }

    @Override
    public QuestionDetailForTeacherResponseDto update(Long id, QuestionRequestDto question, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Question existQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "Id", id));

        existQuestion.setOrder(question.getOrder());
        existQuestion.setScore(question.getScore());
        existQuestion.setLevel(question.getLevel());
        existQuestion.setContent(question.getContent());
        existQuestion.setQuestionType(question.getQuestionType());

        Question savedQuestion = questionRepository.save(existQuestion);
        return modelMapperUtil.mapOne(savedQuestion, QuestionDetailForTeacherResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Question existQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "Id", id));

        existQuestion.setIsDeleted(!existQuestion.getIsDeleted());
        Question savedQuestion = questionRepository.save(existQuestion);
        return savedQuestion.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id) {
        Question existQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "Id", id));
        questionRepository.delete(existQuestion);
        return true;

    }
}
