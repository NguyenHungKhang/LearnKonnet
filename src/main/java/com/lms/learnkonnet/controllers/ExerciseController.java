package com.lms.learnkonnet.controllers;


import com.lms.learnkonnet.dtos.requests.excercise.ExerciseAccessWPasswordRequestDto;
import com.lms.learnkonnet.dtos.requests.excercise.ExerciseRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;

import com.lms.learnkonnet.dtos.responses.excercise.ExerciseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.ExerciseSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.excercise.PasswordAccessExerciseResponseDto;
import com.lms.learnkonnet.exceptions.ApiResponse;
import com.lms.learnkonnet.services.*;
import com.lms.learnkonnet.services.impls.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/exercise")
public class ExerciseController {
    @Autowired
    private ICourseService courseService = new CourseService();
    @Autowired
    private IUserService userService = new UserService();
    @Autowired
    private IMemberService memberService = new MemberService();
    @Autowired
    private IExerciseService exerciseService = new ExerciseService();
    @Autowired
    private ISectionService sectionService = new SectionService();

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody ExerciseRequestDto exercise, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        ExerciseInfoResponseDto newExercise = exerciseService.add(exercise, currentUserId);
        return new ResponseEntity<ExerciseInfoResponseDto>(newExercise, HttpStatus.CREATED);
    }

    // update
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody ExerciseRequestDto exercise, @PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        ExerciseInfoResponseDto updatedExercise = exerciseService.update(id, exercise, currentUserId);
        return new ResponseEntity<ExerciseInfoResponseDto>(updatedExercise, HttpStatus.OK);
    }

    // soft delete
    @PutMapping("/soft-delete/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isSoftDeletedExercise = exerciseService.softDelete(id, currentUserId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Exercise soft deleted stauts: " + isSoftDeletedExercise, true), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isDeletedExercise = exerciseService.delete(id, currentUserId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Exercise deleted status: " + isDeletedExercise, true), HttpStatus.OK);
    }

    @GetMapping("/list-pageable/course/{courseId}")
    public ResponseEntity<?> getAllPageableListByCourse(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "createdAt") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @PathVariable Long courseId,
            Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        PageResponse<ExerciseSumaryResponseDto> exercises = exerciseService.getPageableListByCourse(
                keyword, sortField, sortDir, pageNum, pageSize, currentUserId, courseId);
        return new ResponseEntity<PageResponse<ExerciseSumaryResponseDto>>(exercises, HttpStatus.OK);
    }

    @GetMapping("/list/course/{courseId}")
    public ResponseEntity<?> getAllByCourse(@PathVariable Long topcId, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        List<ExerciseSumaryResponseDto> exercises = exerciseService.getAllByCourse(topcId, currentUserId);
        return new ResponseEntity<List<ExerciseSumaryResponseDto>>(exercises, HttpStatus.OK);
    }

    // get by id
    @GetMapping("/{id}/detail")
    public ResponseEntity<?> getOneDetail(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        ExerciseDetailResponseDto exercise = exerciseService.getDetailById(id, currentUserId);
        return new ResponseEntity<ExerciseDetailResponseDto>(exercise, HttpStatus.OK);
    }

    @GetMapping("/{id}/sumary")
    public ResponseEntity<?> getOneSumary(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        ExerciseSumaryResponseDto exercise = exerciseService.getSumaryById(id, currentUserId);
        return new ResponseEntity<ExerciseSumaryResponseDto>(exercise, HttpStatus.OK);
    }

    @GetMapping("/{id}/info")
    public ResponseEntity<?> getOneInfo(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        ExerciseInfoResponseDto exercise = exerciseService.getInfoById(id, currentUserId);
        return new ResponseEntity<ExerciseInfoResponseDto>(exercise, HttpStatus.OK);
    }

    @GetMapping("/{id}/password")
    public ResponseEntity<?> getOnePassword(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        PasswordAccessExerciseResponseDto password = exerciseService.getPasswordExercise(id, currentUserId);
        return new ResponseEntity<PasswordAccessExerciseResponseDto>(password, HttpStatus.OK);
    }

    @PostMapping("/{id}/password")
    public ResponseEntity<?> accessExerciseWPassword(@PathVariable Long id, @RequestBody ExerciseAccessWPasswordRequestDto accessPassword, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean accessible = exerciseService.accessExcersiseWPassword(id, accessPassword, currentUserId);

        // Need to add service that make member attemp

        return new ResponseEntity<ApiResponse>(new ApiResponse("Exercise access status: " + accessible, true), HttpStatus.OK);
    }
}
