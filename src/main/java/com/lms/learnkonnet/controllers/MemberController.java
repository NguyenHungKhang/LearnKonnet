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
    public ResponseEntity<?> add(@RequestBody MemberRequestDto member, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Course existCourse = courseRepository.findById(member.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Code", member.getCourseId()));

        if (existCourse.getUser().getId().equals(currentUserId) ||
                (member.getUserId().equals(currentUserId) &&
                        memberRepository.findByUserIdAndCourseId(member.getUserId(), member.getCourseId()).isEmpty() &&
                        member.getStatus().equals(MemberStatus.WAIT))) {
            MemberDetailResponseDto newMember = memberService.add(member, currentUserId);
            return new ResponseEntity<MemberDetailResponseDto>(newMember, HttpStatus.CREATED);
        }

        return new ResponseEntity<ApiResponse>(new ApiResponse("User cannot add member into this course", true), HttpStatus.OK);
    }
    // update
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody MemberRequestDto member, @PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Course existCourse = courseRepository.findById(member.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Code", member.getCourseId()));

        if (existCourse.getUser().getId().equals(currentUserId) ||
                (member.getUserId().equals(currentUserId) &&
                        memberRepository.findByUserIdAndCourseId(member.getUserId(), member.getCourseId()).isEmpty() &&
                        member.getStatus().equals(MemberStatus.ACTIVED))) {
            MemberDetailResponseDto newMember = memberService.update(id, member, currentUserId);
            return new ResponseEntity<MemberDetailResponseDto>(newMember, HttpStatus.OK);
        }

        return new ResponseEntity<ApiResponse>(new ApiResponse("User cannot update member into this course", true), HttpStatus.OK);
    }

    // soft delete
    @PostMapping("/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "Id", id));

        if (existMember.getUser().getId().equals(currentUserId) &&
                existMember.getStatus().equals(MemberStatus.ACTIVED)) {
            Boolean isSoftDeletedMember = memberService.softDelete(id, currentUserId);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Member soft deleted stauts: " + isSoftDeletedMember, true), HttpStatus.OK);
        }

        Course existCourse = existMember.getCourse();
        if (existCourse != null && existCourse.getUser().getId().equals(currentUserId)) {
            Boolean isSoftDeletedMember = memberService.softDelete(id, currentUserId);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Member soft deleted stauts: " + isSoftDeletedMember, true), HttpStatus.OK);
        }

        return new ResponseEntity<ApiResponse>(new ApiResponse("User cannot soft delete member into this course", false), HttpStatus.FORBIDDEN);
    }

    // delete
    @PostMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "Id", id));

        if (existMember.getUser().getId().equals(currentUserId) &&
                existMember.getStatus().equals(MemberStatus.ACTIVED)) {
            Boolean isSoftDeletedMember = memberService.delete(id);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Member deleted status: " + isSoftDeletedMember, true), HttpStatus.OK);
        }

        Course existCourse = existMember.getCourse();
        if (existCourse != null && existCourse.getUser().getId().equals(currentUserId)) {
            Boolean isSoftDeletedMember = memberService.delete(id);
            return new ResponseEntity<ApiResponse>(new ApiResponse("Member deleted status: " + isSoftDeletedMember, true), HttpStatus.OK);
        }

        return new ResponseEntity<ApiResponse>(new ApiResponse("User cannot delete member into this course", false), HttpStatus.FORBIDDEN);
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
