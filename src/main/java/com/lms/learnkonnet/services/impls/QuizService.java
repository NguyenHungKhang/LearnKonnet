package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.quiz.FullQuizRequestDto;
import com.lms.learnkonnet.dtos.requests.quiz.QuizRequestDto;
import com.lms.learnkonnet.dtos.responses.question.QuestionDetailForTeacherResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizDetailForTeacherResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizSumaryResponseDto;
import com.lms.learnkonnet.exceptions.ApiException;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.models.enums.Status;
import com.lms.learnkonnet.repositories.*;
import com.lms.learnkonnet.services.IQuestionService;
import com.lms.learnkonnet.services.IQuizService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService implements IQuizService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IQuestionService questionService;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IExerciseRepository exerciseRepository;
    @Autowired
    private IQuizRepository quizRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Override
    public QuizDetailForStudentResponseDto getDetailByStudentAndId(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Quiz existQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));
        Exercise exercise = exerciseRepository.findById(existQuiz.getExercise().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", existQuiz.getExercise().getId()));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, exercise.getCourse().getId());

        if (!(currentUserMember.isPresent() &&
                currentUserMember.get().getType().equals(MemberType.STUDENT) &&
                currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem bài tập");

        if (!existQuiz.getExercise().getStatus().equals(Status.AVAIABLE))
            throw new ApiException("Học sinh không thể truy cập bài tập này");

        return modelMapperUtil.mapOne(existQuiz, QuizDetailForStudentResponseDto.class);
    }

    @Override
    public QuizDetailForTeacherResponseDto getDetailByTeacherAndId(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Quiz existQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));
        Exercise exercise = exerciseRepository.findById(existQuiz.getExercise().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", existQuiz.getExercise().getId()));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, exercise.getCourse().getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem bài tập");


        return modelMapperUtil.mapOne(existQuiz, QuizDetailForTeacherResponseDto.class);
    }

    @Override
    public QuizSumaryResponseDto getSumaryById(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Quiz existQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));
        Exercise exercise = exerciseRepository.findById(existQuiz.getExercise().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", existQuiz.getExercise().getId()));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, exercise.getCourse().getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem bài tập");

        if (currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT))
            if (!existQuiz.getExercise().getStatus().equals(Status.AVAIABLE))
                throw new ApiException("Học sinh không thể truy cập bài tập này");

            return modelMapperUtil.mapOne(existQuiz, QuizSumaryResponseDto.class);
    }

    @Override
    public QuizSumaryResponseDto add(FullQuizRequestDto quiz, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Exercise exercise = exerciseRepository.findById(quiz.getExerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", quiz.getExerciseId()));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, exercise.getCourse().getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm hay chỉnh sửa bài tập");

        if (quiz.getIsLimitAttempts().equals(true) && (quiz.getAttempts() == null || quiz.getAttempts() <= 0))
            throw new ApiException("Số lần thực hiện bài tập không hợp lệ");

        if (quiz.getAttempts() == null && quiz.getAttempts() > 0)
            quiz.setIsLimitAttempts(true);

//        if (quiz.getIsLimitNumberOfQuestion().equals(true) && (quiz.getNumberOfQuestion() == null || quiz.getNumberOfQuestion() < 0))
//            throw new ApiException("Số câu hỏi không hợp lệ");
//
//        if (quiz.getNumberOfQuestion() != null && quiz.getNumberOfQuestion() > 0)
//            quiz.setIsLimitNumberOfQuestion(true);
//
//        if (quiz.getIsQuestionLevelClassification().equals(true)) {
//            int numsOfLvl1 = quiz.getNumsOfLvl1() == null ? 0 : quiz.getNumsOfLvl1();
//            int numsOfLvl2 = quiz.getNumsOfLvl2() == null ? 0 : quiz.getNumsOfLvl2();
//            int numsOfLvl3 = quiz.getNumsOfLvl3() == null ? 0 : quiz.getNumsOfLvl3();
//            if (quiz.getNumberOfQuestion() != null)
//                if (numsOfLvl1 + numsOfLvl2 + numsOfLvl3 != quiz.getNumberOfQuestion())
//                    throw new ApiException("Số câu hỏi không hợp lệ");
//        }

        // add counter of total question and number of question with each level

        Quiz newQuiz = modelMapperUtil.mapOne(quiz, Quiz.class);
        newQuiz.setExercise(exercise);
        Quiz savedQuiz = quizRepository.save(newQuiz);
        quiz.getQuestions().forEach(question -> question.setQuizId(newQuiz.getId()));

        questionService.updateMulti(new ArrayList<>(quiz.getQuestions()), currentUserId);

        return modelMapperUtil.mapOne(savedQuiz, QuizSumaryResponseDto.class);
    }

    @Override
    public QuizSumaryResponseDto update(Long id, FullQuizRequestDto quiz, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Quiz existQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));
        Exercise exercise = exerciseRepository.findById(quiz.getExerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", quiz.getExerciseId()));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, exercise.getCourse().getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm hay chỉnh sửa bài tập");

        if (quiz.getIsLimitAttempts().equals(true) && (quiz.getAttempts() == null || quiz.getAttempts() <= 0))
            throw new ApiException("Số lần thực hiện bài tập không hợp lệ");

        if (quiz.getAttempts() == null && quiz.getAttempts() > 0)
            quiz.setIsLimitAttempts(true);

//        if (quiz.getIsLimitNumberOfQuestion().equals(true) && (quiz.getNumberOfQuestion() == null || quiz.getNumberOfQuestion() < 0))
//            throw new ApiException("Số câu hỏi không hợp lệ");
//
//        if (quiz.getNumberOfQuestion() != null && quiz.getNumberOfQuestion() > 0)
//            quiz.setIsLimitNumberOfQuestion(true);
//
//        if (quiz.getIsQuestionLevelClassification().equals(true)) {
//            int numsOfLvl1 = quiz.getNumsOfLvl1() == null ? 0 : quiz.getNumsOfLvl1();
//            int numsOfLvl2 = quiz.getNumsOfLvl2() == null ? 0 : quiz.getNumsOfLvl2();
//            int numsOfLvl3 = quiz.getNumsOfLvl3() == null ? 0 : quiz.getNumsOfLvl3();
//            if (quiz.getNumberOfQuestion() != null)
//                if (numsOfLvl1 + numsOfLvl2 + numsOfLvl3 != quiz.getNumberOfQuestion())
//                    throw new ApiException("Số câu hỏi không hợp lệ");
//        }

        // add counter of total question and number of question with each level

        existQuiz.setIsMixQuestion(quiz.getIsMixQuestion());
        existQuiz.setIsMixAnswer(quiz.getIsMixAnswer());
        existQuiz.setIsLimitAttempts(quiz.getIsLimitAttempts());
        existQuiz.setAttempts(quiz.getAttempts());
        existQuiz.setGradedType(quiz.getGradedType());
        existQuiz.setIsLimitNumberOfQuestion(quiz.getIsLimitNumberOfQuestion());
        existQuiz.setNumberOfQuestion(quiz.getNumberOfQuestion());
        existQuiz.setIsQuestionLevelClassification(quiz.getIsQuestionLevelClassification());
        existQuiz.setNumsOfLvl1(quiz.getNumsOfLvl1());
        existQuiz.setNumsOfLvl2(quiz.getNumsOfLvl2());
        existQuiz.setNumsOfLvl3(quiz.getNumsOfLvl3());
        existQuiz.setIsReviewed(quiz.getIsReviewed());
        existQuiz.setIsShowScore(quiz.getIsShowScore());
        existQuiz.setIsShowAnswer(quiz.getIsShowAnswer());

        questionService.updateMulti(new ArrayList<>(quiz.getQuestions()), currentUserId);

        Quiz savedQuiz = quizRepository.save(existQuiz);
        return modelMapperUtil.mapOne(savedQuiz, QuizSumaryResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Quiz existQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));
        Exercise exercise = exerciseRepository.findById(existQuiz.getExercise().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", existQuiz.getExercise().getId()));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, exercise.getCourse().getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm hay chỉnh sửa bài tập");

        existQuiz.setIsDeleted(!existQuiz.getIsDeleted());
        Quiz savedQuiz = quizRepository.save(existQuiz);
        return savedQuiz.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Quiz existQuiz = quizRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz", "Id", id));
        Exercise exercise = exerciseRepository.findById(existQuiz.getExercise().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", existQuiz.getExercise().getId()));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, exercise.getCourse().getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm hay chỉnh sửa bài tập");

        quizRepository.delete(existQuiz);
        return true;
    }
}
