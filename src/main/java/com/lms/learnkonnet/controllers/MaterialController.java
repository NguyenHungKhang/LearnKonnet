package com.lms.learnkonnet.controllers;

import com.lms.learnkonnet.dtos.requests.material.MaterialRequestDto;
import com.lms.learnkonnet.dtos.requests.section.SectionRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.material.MaterialDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.section.SectionDetailResponseDto;
import com.lms.learnkonnet.exceptions.ApiResponse;
import com.lms.learnkonnet.services.*;
import com.lms.learnkonnet.services.impls.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

public class MaterialController {
    @Autowired
    private ICourseService courseService = new CourseService();
    @Autowired
    private IUserService userService = new UserService();
    @Autowired
    private IMemberService memberService = new MemberService();
    @Autowired
    private IMaterialService materialService = new MaterialService();
    @Autowired
    private ISectionService sectionService = new SectionService();

    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody MaterialRequestDto material, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        MaterialDetailResponseDto newMaterial = materialService.add(material, currentUserId);
        return new ResponseEntity<MaterialDetailResponseDto>(newMaterial, HttpStatus.CREATED);
    }

    // update
    @PostMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody MaterialRequestDto material, @PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        MaterialDetailResponseDto updatedMaterial = materialService.update(id, material, currentUserId);
        return new ResponseEntity<MaterialDetailResponseDto>(updatedMaterial, HttpStatus.OK);
    }

    // soft delete
    @PutMapping("/soft-delete/{id}")
    public ResponseEntity<?> softDelete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isSoftDeletedMaterial = materialService.softDelete(id, currentUserId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Section soft deleted stauts: " + isSoftDeletedMaterial, true), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        Boolean isDeletedMaterial = materialService.delete(id, currentUserId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Section deleted status: " + isDeletedMaterial, true), HttpStatus.OK);
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
        PageResponse<MaterialDetailResponseDto> materials = materialService.getPageableListByCourse(
                keyword, sortField, sortDir, pageNum, pageSize, currentUserId, courseId);
        return new ResponseEntity<PageResponse<MaterialDetailResponseDto>>(materials, HttpStatus.OK);
    }

    @GetMapping("/list/course/{courseId}")
    public ResponseEntity<?> getAllByCourse(@PathVariable Long topcId, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        List<MaterialDetailResponseDto> materials = materialService.getAllByCourse(topcId, currentUserId);
        return new ResponseEntity<List<MaterialDetailResponseDto>>(materials, HttpStatus.OK);
    }

    // get by id
    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        MaterialDetailResponseDto material = materialService.getById(id, currentUserId);
        return new ResponseEntity<MaterialDetailResponseDto>(material, HttpStatus.OK);
    }
}
