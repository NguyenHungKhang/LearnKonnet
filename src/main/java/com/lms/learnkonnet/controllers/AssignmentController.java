package com.lms.learnkonnet.controllers;

import com.lms.learnkonnet.dtos.requests.assignment.AssignmentRequestDto;
import com.lms.learnkonnet.dtos.requests.quiz.FullQuizRequestDto;
import com.lms.learnkonnet.dtos.requests.quiz.QuizRequestDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizSumaryResponseDto;
import com.lms.learnkonnet.exceptions.ApiResponse;
import com.lms.learnkonnet.services.*;
import com.lms.learnkonnet.services.impls.*;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/assignment")
public class AssignmentController {
    @Autowired
    private ICourseService courseService = new CourseService();
    @Autowired
    private IUserService userService = new UserService();
    @Autowired
    private IMemberService memberService = new MemberService();
    @Autowired
    private IAssignmentService assignmentService = new AssignmentService();
    @Autowired
    private IQuestionService questionService = new QuestionService();
    @Autowired
    private ModelMapperUtil modelMapperUtil;

    // add with quiz option, question and choice
    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody AssignmentRequestDto assignment, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        AssignmentDetailResponseDto newAssignment = assignmentService.add(assignment, currentUserId);
        return new ResponseEntity<AssignmentDetailResponseDto>(newAssignment, HttpStatus.CREATED);
    }

    // update with quiz option, question and choice
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody AssignmentRequestDto assignment, @PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        AssignmentDetailResponseDto updatedAssignment = assignmentService.update(id, assignment, currentUserId);
        return new ResponseEntity<AssignmentDetailResponseDto>(updatedAssignment, HttpStatus.OK);
    }

    // soft delete
    @PutMapping("/soft-delete/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isSoftDeleteAssignment = assignmentService.softDelete(id, currentUserId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Assignment soft deleted status: " + isSoftDeleteAssignment, true), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isDeleteAssignment = assignmentService.delete(id, currentUserId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Assignment deleted status: " + isDeleteAssignment, true), HttpStatus.OK);
    }

}
