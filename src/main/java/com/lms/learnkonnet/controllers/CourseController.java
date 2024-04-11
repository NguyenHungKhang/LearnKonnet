package com.lms.learnkonnet.controllers;

import com.lms.learnkonnet.dtos.requests.course.CourseRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.course.CourseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.course.CourseSumaryResponseDto;
import com.lms.learnkonnet.exceptions.ApiResponse;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.User;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.repositories.IUserRepository;
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
import org.springframework.data.domain.Page;
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
    private IUserRepository userRepository;
    @Autowired
    private IMemberService memberService = new MemberService();
    @Autowired
    private IFileService fileService = new FileService();
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // create
    @PostMapping("/")
    public ResponseEntity<CourseDetailResponseDto> add(@RequestBody CourseRequestDto course, Principal principal) {
        Long currentUserId =userService.checkUserAvaiableByEmail(principal.getName());
        CourseDetailResponseDto newCourse = courseService.add(course, currentUserId);
        return new ResponseEntity<CourseDetailResponseDto>(newCourse, HttpStatus.CREATED);
    }


    // update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody CourseRequestDto course, @PathVariable Long id, Principal principal) {
        Long currentUserId =userService.checkUserAvaiableByEmail(principal.getName());
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
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isDeleted = courseService.delete(id, currentUserId);
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

    // get pageable list by owner
    @GetMapping("/list/owner")
    public ResponseEntity<?> getAllPageableListByOwner(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "createdAt") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        PageResponse<CourseSumaryResponseDto> courses = courseService.getAllPageableListByOwner(
                keyword, sortField, sortDir, pageNum, pageSize, currentUserId);
        return new ResponseEntity<PageResponse<CourseSumaryResponseDto>>(courses, HttpStatus.OK);
    }

    @GetMapping("/list/type-member")
    public ResponseEntity<?> getAllPageableListByMemberTypeAndMemberStatus(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "createdAt") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "ACTIVED") MemberStatus status,
            @RequestParam(defaultValue = "STUDENT") MemberType type,
            Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        PageResponse<CourseSumaryResponseDto> courses = courseService.getAllPageableListByStatusMemberAndMemberType(
                keyword, sortField, sortDir, pageNum, pageSize, currentUserId, status, type);
        return new ResponseEntity<PageResponse<CourseSumaryResponseDto>>(courses, HttpStatus.OK);
    }

    @GetMapping("/list/member")
    public ResponseEntity<?> getAllPageableListByMemberStatus(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "createdAt") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "ACTIVED") MemberStatus status,
            Principal principal) {

        Long currentUserId = userService.getIdByEmail(principal.getName());
        PageResponse<CourseSumaryResponseDto> courses = courseService.getAllPageableListByStatusMember(
                keyword, sortField, sortDir, pageNum, pageSize, currentUserId, status);
        return new ResponseEntity<PageResponse<CourseSumaryResponseDto>>(courses, HttpStatus.OK);
    }

}
