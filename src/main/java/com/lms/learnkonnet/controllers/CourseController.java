package com.lms.learnkonnet.controllers;

import com.lms.learnkonnet.dtos.requests.course.CourseRequestDto;
import com.lms.learnkonnet.dtos.responses.course.CourseDetailResponseDto;
import com.lms.learnkonnet.exceptions.ApiResponse;
import com.lms.learnkonnet.models.User;
import com.lms.learnkonnet.securities.CustomUserDetailsService;
import com.lms.learnkonnet.services.ICourseService;
import com.lms.learnkonnet.services.IFileService;
import com.lms.learnkonnet.services.IMemberService;
import com.lms.learnkonnet.services.IUserService;
import com.lms.learnkonnet.services.impls.CourseService;
import com.lms.learnkonnet.services.impls.FileService;
import com.lms.learnkonnet.services.impls.MemberService;
import com.lms.learnkonnet.services.impls.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/course")
public class CourseController {
    @Autowired
    private ICourseService courseService = new CourseService();
    @Autowired
    private IUserService userService = new UserService();
    @Autowired
    private IMemberService memberService = new MemberService();
    @Autowired
    private IFileService fileService = new FileService();
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // create
    @PostMapping("/")
    public ResponseEntity<CourseDetailResponseDto> add(@RequestBody CourseRequestDto course, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        CourseDetailResponseDto newCourse = courseService.add(course, currentUserId);
        return new ResponseEntity<CourseDetailResponseDto>(newCourse, HttpStatus.CREATED);
    }


    // update
    @PutMapping("/{id}")
    public ResponseEntity<CourseDetailResponseDto> update(@RequestBody CourseRequestDto course, @PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        CourseDetailResponseDto newCourse = courseService.update(id, course, currentUserId);
        return new ResponseEntity<CourseDetailResponseDto>(newCourse, HttpStatus.OK);
    }

    // softDelete
    @PutMapping("/soft-delete/{id}")
    public ResponseEntity<ApiResponse> softDelete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isSoftDelete = courseService.softDelete(id, currentUserId);
        ApiResponse apiResponse = new ApiResponse(isSoftDelete ? "Course stautus: soft deleted" : "Course status: avaiable", true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    // delete
    @PutMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isDeleted = courseService.delete(id ,currentUserId);
        ApiResponse apiResponse = new ApiResponse(isDeleted ? "Course stautus: true deleted" : "Course status: avaiable", true);
        return new ResponseEntity<ApiResponse>(apiResponse, HttpStatus.OK);
    }

    // get one
    @GetMapping("/{id}")
    public ResponseEntity<CourseDetailResponseDto> getOne(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        CourseDetailResponseDto course = courseService.getById(id, currentUserId);
        return new ResponseEntity<CourseDetailResponseDto>(course, HttpStatus.OK);
    }

    // get pageable list by user (stauts AVAIABLE)

    // get pageable list by member and status INVITED

    // get pageable list by member and status WAITED

    // get pageable list by owner
}
