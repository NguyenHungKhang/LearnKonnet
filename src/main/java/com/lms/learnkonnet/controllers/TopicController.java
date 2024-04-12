package com.lms.learnkonnet.controllers;

import com.lms.learnkonnet.dtos.requests.member.MemberRequestDto;
import com.lms.learnkonnet.dtos.requests.topic.TopicRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.member.MemberBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.member.MemberDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;
import com.lms.learnkonnet.exceptions.ApiResponse;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.repositories.ICourseRepository;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.repositories.ITopicRepository;
import com.lms.learnkonnet.services.ICourseService;
import com.lms.learnkonnet.services.IMemberService;
import com.lms.learnkonnet.services.ITopicService;
import com.lms.learnkonnet.services.IUserService;
import com.lms.learnkonnet.services.impls.CourseService;
import com.lms.learnkonnet.services.impls.MemberService;
import com.lms.learnkonnet.services.impls.TopicService;
import com.lms.learnkonnet.services.impls.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/topic")
public class TopicController {
    @Autowired
    private ICourseService courseService = new CourseService();
    @Autowired
    private IUserService userService = new UserService();
    @Autowired
    private IMemberService memberService = new MemberService();
    @Autowired
    private ITopicService topicService = new TopicService();

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody TopicRequestDto topic, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        TopicBasicInfoResponseDto newTopic = topicService.add(topic, currentUserId);
        return new ResponseEntity<TopicBasicInfoResponseDto>(newTopic, HttpStatus.CREATED);
    }

    // update
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody TopicRequestDto topic, @PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        TopicBasicInfoResponseDto updatedTopic = topicService.update(id, topic, currentUserId);
        return new ResponseEntity<TopicBasicInfoResponseDto>(updatedTopic, HttpStatus.OK);
    }

    // soft delete
    @PutMapping("/soft-delete/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isSoftDeletedTopic = topicService.softDelete(id, currentUserId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Member soft deleted stauts: " + isSoftDeletedTopic, true), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isDeletedTopic = topicService.delete(id, currentUserId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Member deleted status: " + isDeletedTopic, true), HttpStatus.OK);
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
        PageResponse<TopicDetailResponseDto> topics = topicService.getPageableList(
                keyword, sortField, sortDir, pageNum, pageSize, currentUserId, courseId);
        return new ResponseEntity<PageResponse<TopicDetailResponseDto>>(topics, HttpStatus.OK);
    }

    @GetMapping("/list/course/{courseId}")
    public ResponseEntity<?> getAllByCourse(@PathVariable Long courseId, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        List<TopicDetailResponseDto> topics = topicService.getAll(courseId, currentUserId);
        return new ResponseEntity<List<TopicDetailResponseDto>>(topics, HttpStatus.OK);
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        TopicDetailResponseDto topic = topicService.getById(id, currentUserId);
        return new ResponseEntity<TopicDetailResponseDto>(topic, HttpStatus.OK);
    }
}
