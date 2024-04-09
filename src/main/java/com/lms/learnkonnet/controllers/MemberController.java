package com.lms.learnkonnet.controllers;

import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.course.CourseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.course.CourseSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.member.MemberBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.member.MemberDetailResponseDto;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
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

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {
    @Autowired
    private ICourseService courseService = new CourseService();
    @Autowired
    private IUserService userService = new UserService();
    @Autowired
    private IMemberService memberService = new MemberService();
    // create

    // update

    // soft delete

    // delete

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
