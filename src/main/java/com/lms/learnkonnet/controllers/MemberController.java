package com.lms.learnkonnet.controllers;

import com.lms.learnkonnet.dtos.requests.course.CourseRequestDto;
import com.lms.learnkonnet.dtos.requests.member.MemberRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.course.CourseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.course.CourseSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.member.MemberBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.member.MemberDetailResponseDto;
import com.lms.learnkonnet.exceptions.ApiResponse;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.repositories.ICourseRepository;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.services.ICourseService;
import com.lms.learnkonnet.services.IMemberService;
import com.lms.learnkonnet.services.IUserService;
import com.lms.learnkonnet.services.impls.CourseService;
import com.lms.learnkonnet.services.impls.MemberService;
import com.lms.learnkonnet.services.impls.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {
    @Autowired
    private ICourseService courseService = new CourseService();
    @Autowired
    private IUserService userService = new UserService();
    @Autowired
    private IMemberService memberService = new MemberService();
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IMemberRepository memberRepository;
    // create
    @PostMapping("/")
    public ResponseEntity<?> add(@Valid @RequestBody MemberRequestDto member, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        MemberDetailResponseDto newMember = memberService.add(member, currentUserId);
        return new ResponseEntity<MemberDetailResponseDto>(newMember, HttpStatus.CREATED);
    }

    // update
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody MemberRequestDto member, @PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        MemberDetailResponseDto newMember = memberService.update(id, member, currentUserId);
        return new ResponseEntity<MemberDetailResponseDto>(newMember, HttpStatus.OK);
    }

    // soft delete
    @PutMapping("/soft-delete/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isSoftDeletedMember = memberService.softDelete(id, currentUserId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Member soft deleted stauts: " + isSoftDeletedMember, true), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isSoftDeletedMember = memberService.delete(id, currentUserId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Member deleted status: " + isSoftDeletedMember, true), HttpStatus.OK);
    }

    @GetMapping("/list/course/{courseId}")
    public ResponseEntity<?> getAllPageableListByMemberTypeAndMemberStatus(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "createdAt") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "ACTIVED") MemberStatus status,
            @RequestParam(defaultValue = "STUDENT") MemberType type,
            @PathVariable Long courseId,
            Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        PageResponse<MemberBasicInfoResponseDto> members = memberService.getStudentInfoPageableListByMemberAndType(
                keyword, sortField, sortDir, pageNum, pageSize, currentUserId, courseId, status, type);
        return new ResponseEntity<PageResponse<MemberBasicInfoResponseDto>>(members, HttpStatus.OK);
    }

    @GetMapping("/list/course/{courseId}/student")
    public ResponseEntity<?> getAllStudent(@PathVariable Long courseId, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        List<MemberBasicInfoResponseDto> member = memberService.getAllStudentInfo(courseId, currentUserId);
        return new ResponseEntity<List<MemberBasicInfoResponseDto>>(member, HttpStatus.OK);
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        MemberDetailResponseDto member = memberService.getDetailById(id, currentUserId);
        return new ResponseEntity<MemberDetailResponseDto>(member, HttpStatus.OK);
    }
}
