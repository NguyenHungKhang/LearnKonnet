package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.assignment.AssignmentRequestDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizSumaryResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.Assignment;
import com.lms.learnkonnet.models.Exercise;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Quiz;
import com.lms.learnkonnet.repositories.IAssignmentRepository;
import com.lms.learnkonnet.repositories.IExerciseRepository;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.repositories.IQuizRepository;
import com.lms.learnkonnet.services.IAssignmentService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentService implements IAssignmentService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IExerciseRepository exerciseRepository;
    @Autowired
    private IAssignmentRepository assignmentRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public AssignmentDetailResponseDto getDetailById(Long id) {
        Assignment existAssignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment", "Id", id));
        return modelMapperUtil.mapOne(existAssignment, AssignmentDetailResponseDto.class);
    }

    @Override
    public AssignmentSumaryResponseDto getSumaryById(Long id) {
        Assignment existAssignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment", "Id", id));
        return modelMapperUtil.mapOne(existAssignment, AssignmentSumaryResponseDto.class);
    }

    @Override
    public AssignmentDetailResponseDto add(AssignmentRequestDto assignment, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Exercise exercise = exerciseRepository.findById(assignment.getExerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", assignment.getExerciseId()));

        Assignment newAssignment= modelMapperUtil.mapOne(assignment, Assignment.class);
        newAssignment.setExercise(exercise);
        Assignment savedAssignment = assignmentRepository.save(newAssignment);
        return modelMapperUtil.mapOne(savedAssignment, AssignmentDetailResponseDto.class);
    }

    @Override
    public AssignmentDetailResponseDto update(Long id, AssignmentRequestDto assignment, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Assignment existAssignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment", "Id", id));

        existAssignment.setName(assignment.getName());
        existAssignment.setAssignmentType(assignment.getAssignmentType());
        existAssignment.setIsResubmit(assignment.getIsResubmit());
        existAssignment.setIsAcceptText(assignment.getIsAcceptText());
        existAssignment.setIsAcceptFile(assignment.getIsAcceptFile());

        Assignment savedAssignment = assignmentRepository.save(existAssignment);
        return modelMapperUtil.mapOne(savedAssignment, AssignmentDetailResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Assignment existAssignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment", "Id", id));

        existAssignment.setIsDeleted(!existAssignment.getIsDeleted());
        Assignment savedAssignment = assignmentRepository.save(existAssignment);
        return savedAssignment.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id) {
        Assignment existAssignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment", "Id", id));
        assignmentRepository.delete(existAssignment);
        return true;
    }
}
