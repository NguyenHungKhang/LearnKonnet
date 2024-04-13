package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.question.QuestionRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.material.MaterialDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.question.QuestionDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.question.QuestionDetailForTeacherResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizSumaryResponseDto;
import com.lms.learnkonnet.exceptions.ApiException;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.repositories.*;
import com.lms.learnkonnet.services.IQuestionService;
import com.lms.learnkonnet.services.IUserService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService implements IQuestionService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IQuestionRepository questionRepository;
    @Autowired
    private IQuizRepository quizRepository;
    @Autowired
    private ICourseRepository courseRepository;
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
    public List<QuestionDetailForTeacherResponseDto> updateMulti(List<QuestionRequestDto> questions, Long currentUserId) {
        Long quizId = isValidListQuestion(questions, currentUserId);
        Quiz quiz = quizRepository.findById(quizId).get();

        List<Question> existQuestions = quiz.getQuestions();

        for (QuestionRequestDto q : questions) {
            if (q.getId() == null) {
                add(q);
            }
        }

        for (Question existQuestion : existQuestions) {
            boolean found = questions.stream()
                    .anyMatch(dto -> dto.getId() != null && dto.getId().equals(existQuestion.getId()));
            if (!found) {
                delete(existQuestion.getId());
            }
        }


        for (Question existQuestion : existQuestions) {
            for (QuestionRequestDto questionDto : questions) {
                if (questionDto.getId() != null && questionDto.getId().equals(existQuestion.getId())) {
                    update(questionDto.getId(), questionDto);
                    break;
                }
            }
        }

        return modelMapperUtil.mapList(quiz.getQuestions(), QuestionDetailForTeacherResponseDto.class);
    }

    @Override
    public QuestionDetailForTeacherResponseDto add(QuestionRequestDto question) {
        Quiz quiz = quizRepository.findById(question.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", question.getQuizId()));

        Question newQuestion = modelMapperUtil.mapOne(question, Question.class);
        newQuestion.setQuiz(quiz);
        Question savedQuestion = questionRepository.save(newQuestion);
        return modelMapperUtil.mapOne(savedQuestion, QuestionDetailForTeacherResponseDto.class);
    }

    @Override
    public QuestionDetailForTeacherResponseDto update(Long id, QuestionRequestDto question) {
        Quiz quiz = quizRepository.findById(question.getQuizId())
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", question.getQuizId()));
        Question existQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Question", "Id", id));

        existQuestion.setOrder(question.getOrder());
        existQuestion.setScore(question.getScore());
        existQuestion.setContent(question.getContent());
        existQuestion.setQuestionType(question.getQuestionType());

        Question savedQuestion = questionRepository.save(existQuestion);
        return modelMapperUtil.mapOne(savedQuestion, QuestionDetailForTeacherResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id) {
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

    public Long isValidListQuestion(List<QuestionRequestDto> questions, Long currentUserId) {
        HashSet<Integer> orderSet = new HashSet<>();
        Long quizId = questions.getFirst().getQuizId();
        Long finalQuizId = quizId;
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Quiz quiz = quizRepository.findById(finalQuizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", finalQuizId));
        Course course = courseRepository.findById(quiz.getExercise().getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", quiz.getExercise().getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm hay chỉnh sửa bài tập");


        for (QuestionRequestDto question : questions) {
            if (orderSet.contains(question.getOrder())) {
                throw new ApiException("Số thứ tự câu hỏi không hợp lệ");
            } else {
                orderSet.add(question.getOrder());
            }

            if (quizId == null) {
                quizId = question.getQuizId();
            } else {
                if (!quizId.equals(question.getQuizId())) {
                    throw new ApiException("Câu hỏi không tới từ cùng một bài tập");
                }
            }
        }
        return quizId;
    }
}
