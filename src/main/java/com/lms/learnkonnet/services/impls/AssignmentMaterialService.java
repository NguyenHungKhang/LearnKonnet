package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.assignment.AssignmentRequestDto;
import com.lms.learnkonnet.dtos.requests.assignment.material.AssignmentMaterialRequestDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.assignment.material.AssignmentMaterialResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.question.QuestionDetailForStudentResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizSumaryResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.repositories.*;
import com.lms.learnkonnet.services.IAssignmentMaterialService;
import com.lms.learnkonnet.services.IAssignmentService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentMaterialService implements IAssignmentMaterialService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IAssignmentMaterialRepository assignmentMaterialRepository;
    @Autowired
    private IAssignmentRepository assignmentRepository;
    @Autowired
    private IFileRepository fileRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;

    @Override
    public PageResponse<AssignmentMaterialResponseDto> getPageableListByAssignment(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long assignmentId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<AssignmentMaterial> assignmentMaterialsPage = assignmentMaterialRepository.findByAssignment_Id(assignmentId, keyword, pageable);
        List<AssignmentMaterialResponseDto> assignmentMaterialsDtoPage = modelMapperUtil.mapList(assignmentMaterialsPage.getContent(), AssignmentMaterialResponseDto.class);

        return new PageResponse<>(
                assignmentMaterialsDtoPage,
                assignmentMaterialsPage.getNumber(),
                assignmentMaterialsPage.getSize(),
                assignmentMaterialsPage.getTotalElements(),
                assignmentMaterialsPage.getTotalPages(),
                assignmentMaterialsPage.isLast()
        );
    }

    @Override
    public AssignmentMaterialResponseDto getDetailById(Long id) {
        AssignmentMaterial existAssignmentMaterial = assignmentMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File", "Id", id));
        return modelMapperUtil.mapOne(existAssignmentMaterial, AssignmentMaterialResponseDto.class);
    }

    @Override
    public List<AssignmentDetailResponseDto> multiUpdate(List<AssignmentMaterialRequestDto> assignmentMaterial, Long currentUserId) {
        return null;
    }

    @Override
    public AssignmentDetailResponseDto add(AssignmentMaterialRequestDto assignmentMaterial, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Assignment assignment = assignmentRepository.findById(assignmentMaterial.getAssignmentId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", assignmentMaterial.getAssignmentId()));
        File file = fileRepository.findById(assignmentMaterial.getFileId())
                .orElseThrow(() -> new ResourceNotFoundException("File", "Id", assignmentMaterial.getFileId()));

        AssignmentMaterial newAssignmentMaterial = modelMapperUtil.mapOne(assignmentMaterial, AssignmentMaterial.class);
        newAssignmentMaterial.setFile(file);
        newAssignmentMaterial.setAssignment(assignment);
        AssignmentMaterial savedAssignmentMaterial = assignmentMaterialRepository.save(newAssignmentMaterial);
        return modelMapperUtil.mapOne(savedAssignmentMaterial, AssignmentDetailResponseDto.class);
    }

    @Override
    public AssignmentDetailResponseDto update(Long id, AssignmentMaterialRequestDto assignmentMaterial, Long currentMemberId) {
        return null;
    }

    @Override
    public Boolean softDelete(Long id, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        AssignmentMaterial existAssignmentMaterial = assignmentMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File", "Id", id));

        existAssignmentMaterial.setIsDeleted(!existAssignmentMaterial.getIsDeleted());
        AssignmentMaterial savedAssignmentMaterial = assignmentMaterialRepository.save(existAssignmentMaterial);
        return savedAssignmentMaterial.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id) {
        AssignmentMaterial existAssignmentMaterial = assignmentMaterialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File", "Id", id));
        assignmentMaterialRepository.delete(existAssignmentMaterial);
        return true;
    }
}
