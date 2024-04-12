package com.lms.learnkonnet.controllers;

import com.lms.learnkonnet.dtos.requests.section.SectionRequestDto;
import com.lms.learnkonnet.dtos.requests.topic.TopicRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.section.SectionDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;
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
@RequestMapping("/api/v1/section")
public class SectionController {
    @Autowired
    private ICourseService courseService = new CourseService();
    @Autowired
    private IUserService userService = new UserService();
    @Autowired
    private IMemberService memberService = new MemberService();
    @Autowired
    private ITopicService topicService = new TopicService();
    @Autowired
    private ISectionService sectionService = new SectionService();

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody SectionRequestDto section, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        SectionDetailResponseDto newSection = sectionService.add(section, currentUserId);
        return new ResponseEntity<SectionDetailResponseDto>(newSection, HttpStatus.CREATED);
    }

    // update
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody SectionRequestDto section, @PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        SectionDetailResponseDto updatedSection = sectionService.update(id, section, currentUserId);
        return new ResponseEntity<SectionDetailResponseDto>(updatedSection, HttpStatus.OK);
    }

    // soft delete
    @PutMapping("/soft-delete/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isSoftDeletedSection = sectionService.softDelete(id, currentUserId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Section soft deleted stauts: " + isSoftDeletedSection, true), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isDeletedSection = sectionService.delete(id, currentUserId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Section deleted status: " + isDeletedSection, true), HttpStatus.OK);
    }

    @GetMapping("/list-pageable/topic/{topicId}")
    public ResponseEntity<?> getAllPageableListByMemberTypeAndMemberStatus(
            @RequestParam(required = false, defaultValue = "") String keyword,
            @RequestParam(required = false, defaultValue = "createdAt") String sortField,
            @RequestParam(required = false, defaultValue = "asc") String sortDir,
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize,
            @PathVariable Long topicId,
            Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        PageResponse<SectionDetailResponseDto> sections = sectionService.getPageableListByTopic(
                keyword, sortField, sortDir, pageNum, pageSize, currentUserId, topicId);
        return new ResponseEntity<PageResponse<SectionDetailResponseDto>>(sections, HttpStatus.OK);
    }

    @GetMapping("/list/topic/{topcId}")
    public ResponseEntity<?> getAllStudent(@PathVariable Long topcId, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        List<SectionDetailResponseDto> sections = sectionService.getAllByTopic(topcId, currentUserId);
        return new ResponseEntity<List<SectionDetailResponseDto>>(sections, HttpStatus.OK);
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        SectionDetailResponseDto section = sectionService.getById(id, currentUserId);
        return new ResponseEntity<SectionDetailResponseDto>(section, HttpStatus.OK);
    }
}
