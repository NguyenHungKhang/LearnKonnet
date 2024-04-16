package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.assignment.AssignmentRequestDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.quiz.QuizSumaryResponseDto;
import com.lms.learnkonnet.exceptions.ApiException;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.models.enums.AssignmentType;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.repositories.*;
import com.lms.learnkonnet.services.IAssignmentService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignmentService implements IAssignmentService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IExerciseRepository exerciseRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IAssignmentRepository assignmentRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public AssignmentDetailResponseDto getDetailById(Long id) {
//        Assignment existAssignment = assignmentRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Assignment", "Id", id));
//        return modelMapperUtil.mapOne(existAssignment, AssignmentDetailResponseDto.class);
        return null;
    }

    @Override
    public AssignmentSumaryResponseDto getSumaryById(Long id) {
//        Assignment existAssignment = assignmentRepository.findById(id)
//                .orElseThrow(() -> new ResourceNotFoundException("Assignment", "Id", id));
//        return modelMapperUtil.mapOne(existAssignment, AssignmentSumaryResponseDto.class);
        return null;
    }

    @Override
    public AssignmentDetailResponseDto add(AssignmentRequestDto assignment, Long currentUserId) {

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Exercise exercise = exerciseRepository.findById(assignment.getExerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", assignment.getExerciseId()));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, exercise.getCourse().getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm hay chỉnh sửa bài tập");

        if(assignment.getAssignmentType().equals(AssignmentType.RICH_TEXT)) {
            assignment.setIsAcceptText(true);
            assignment.setIsAcceptFile(false);
        }

        if(assignment.getAssignmentType().equals(AssignmentType.FILE)) {
            assignment.setIsAcceptText(false);
            assignment.setIsAcceptFile(true);
        }

        Assignment newAssignment= modelMapperUtil.mapOne(assignment, Assignment.class);
        newAssignment.setExercise(exercise);
        Assignment savedAssignment = assignmentRepository.save(newAssignment);
        return modelMapperUtil.mapOne(savedAssignment, AssignmentDetailResponseDto.class);
    }

    @Override
    public AssignmentDetailResponseDto update(Long id, AssignmentRequestDto assignment, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Assignment existAssignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment", "Id", id));
        Exercise exercise = exerciseRepository.findById(assignment.getExerciseId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", assignment.getExerciseId()));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, exercise.getCourse().getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm hay chỉnh sửa bài tập");

        if(assignment.getAssignmentType().equals(AssignmentType.RICH_TEXT)) {
            assignment.setIsAcceptText(true);
            assignment.setIsAcceptFile(false);
        }

        if(assignment.getAssignmentType().equals(AssignmentType.FILE)) {
            assignment.setIsAcceptText(false);
            assignment.setIsAcceptFile(true);
        }


        existAssignment.setName(assignment.getName());
        existAssignment.setAssignmentType(assignment.getAssignmentType());
        existAssignment.setIsResubmit(assignment.getIsResubmit());
        existAssignment.setIsAcceptText(assignment.getIsAcceptText());
        existAssignment.setIsAcceptFile(assignment.getIsAcceptFile());

        Assignment savedAssignment = assignmentRepository.save(existAssignment);
        return modelMapperUtil.mapOne(savedAssignment, AssignmentDetailResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Assignment existAssignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment", "Id", id));
        Exercise exercise = exerciseRepository.findById(existAssignment.getExercise().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", existAssignment.getExercise().getId()));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, exercise.getCourse().getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm hay chỉnh sửa bài tập");

        existAssignment.setIsDeleted(!existAssignment.getIsDeleted());
        Assignment savedAssignment = assignmentRepository.save(existAssignment);
        return savedAssignment.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Assignment existAssignment = assignmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment", "Id", id));
        Exercise exercise = exerciseRepository.findById(existAssignment.getExercise().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "Id", existAssignment.getExercise().getId()));
        Course course = courseRepository.findById(exercise.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", exercise.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, exercise.getCourse().getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm hay chỉnh sửa bài tập");

        assignmentRepository.delete(existAssignment);
        return true;
    }
}
