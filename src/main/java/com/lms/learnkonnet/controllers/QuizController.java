package com.lms.learnkonnet.controllers;

import com.lms.learnkonnet.dtos.requests.quiz.QuizRequestDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizDetailForTeacherResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizSumaryResponseDto;
import com.lms.learnkonnet.exceptions.ApiResponse;
import com.lms.learnkonnet.services.*;
import com.lms.learnkonnet.services.impls.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/quiz")
public class QuizController {
    @Autowired
    private ICourseService courseService = new CourseService();
    @Autowired
    private IUserService userService = new UserService();
    @Autowired
    private IMemberService memberService = new MemberService();
    @Autowired
    private IQuizService quizService = new QuizService();
    @Autowired
    private ISectionService sectionService = new SectionService();

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody QuizRequestDto quiz, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        QuizSumaryResponseDto newQuiz = quizService.add(quiz, currentUserId);
        return new ResponseEntity<QuizSumaryResponseDto>(newQuiz, HttpStatus.CREATED);
    }

    // update
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody QuizRequestDto quiz, @PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        QuizSumaryResponseDto updatedQuiz = quizService.update(id, quiz, currentUserId);
        return new ResponseEntity<QuizSumaryResponseDto>(updatedQuiz, HttpStatus.OK);
    }

    // soft delete
    @PutMapping("/soft-delete/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isSoftDeletedQuiz = quizService.softDelete(id, currentUserId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Quiz soft deleted status: " + isSoftDeletedQuiz, true), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isDeletedQuiz = quizService.delete(id, currentUserId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Quiz deleted status: " + isDeletedQuiz, true), HttpStatus.OK);
    }


    @GetMapping("/{id}/detail/student")
    public ResponseEntity<?> getByIdForStudent(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        QuizDetailForStudentResponseDto quiz = quizService.getDetailByStudentAndId(id, currentUserId);
        return new ResponseEntity<QuizDetailForStudentResponseDto>(quiz, HttpStatus.OK);
    }

    @GetMapping("/{id}/detail/teacher")
    public ResponseEntity<?> getByIdForTeacher(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        QuizDetailForTeacherResponseDto quiz = quizService.getDetailByTeacherAndId(id, currentUserId);
        return new ResponseEntity<QuizDetailForTeacherResponseDto>(quiz, HttpStatus.OK);
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        QuizSumaryResponseDto quiz = quizService.getSumaryById(id, currentUserId);
        return new ResponseEntity<QuizSumaryResponseDto>(quiz, HttpStatus.OK);
    }
}
