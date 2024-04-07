package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.comment.CommentRequestDto;
import com.lms.learnkonnet.dtos.requests.member.MemberRequestDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.assignment.AssignmentSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.comment.CommentResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.member.MemberBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.member.MemberDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.repositories.*;
import com.lms.learnkonnet.services.IMemberService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService implements IMemberService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public PageResponse<MemberBasicInfoResponseDto> getInfoPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Member> membersPage = memberRepository.findByCourseIdAndUserFamilyNameContainingOrUserGivenNameContainingOrUserEmailContaining(courseId, keyword, pageable);
        List<MemberBasicInfoResponseDto> membersDtoPage = modelMapperUtil.mapList(membersPage.getContent(), MemberBasicInfoResponseDto.class);

        return new PageResponse<>(
                membersDtoPage,
                membersPage.getNumber(),
                membersPage.getSize(),
                membersPage.getTotalElements(),
                membersPage.getTotalPages(),
                membersPage.isLast()
        );
    }

    @Override
    public PageResponse<MemberDetailResponseDto> getDetailPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Member> membersPage = memberRepository.findByCourseIdAndUserFamilyNameContainingOrUserGivenNameContainingOrUserEmailContaining(courseId, keyword, pageable);
        List<MemberDetailResponseDto> membersDtoPage = modelMapperUtil.mapList(membersPage.getContent(), MemberDetailResponseDto.class);

        return new PageResponse<>(
                membersDtoPage,
                membersPage.getNumber(),
                membersPage.getSize(),
                membersPage.getTotalElements(),
                membersPage.getTotalPages(),
                membersPage.isLast()
        );
    }

    @Override
    public PageResponse<MemberBasicInfoResponseDto> getStudentInfoPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Member> membersPage = memberRepository.findByTypeAndCourseIdAndUserFamilyNameContainingOrUserGivenNameContainingOrUserEmailContaining(MemberType.STUDENT, courseId, keyword, pageable);
        List<MemberBasicInfoResponseDto> membersDtoPage = modelMapperUtil.mapList(membersPage.getContent(), MemberBasicInfoResponseDto.class);

        return new PageResponse<>(
                membersDtoPage,
                membersPage.getNumber(),
                membersPage.getSize(),
                membersPage.getTotalElements(),
                membersPage.getTotalPages(),
                membersPage.isLast()
        );
    }

    @Override
    public PageResponse<MemberDetailResponseDto> getStudentDetailPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Member> membersPage = memberRepository.findByTypeAndCourseIdAndUserFamilyNameContainingOrUserGivenNameContainingOrUserEmailContaining(MemberType.STUDENT, courseId, keyword, pageable);
        List<MemberDetailResponseDto> membersDtoPage = modelMapperUtil.mapList(membersPage.getContent(), MemberDetailResponseDto.class);

        return new PageResponse<>(
                membersDtoPage,
                membersPage.getNumber(),
                membersPage.getSize(),
                membersPage.getTotalElements(),
                membersPage.getTotalPages(),
                membersPage.isLast()
        );
    }

    @Override
    public List<MemberDetailResponseDto> getAll(Long courseId) {
        List<Member> members = memberRepository.findAllByCourseId(courseId);
        return modelMapperUtil.mapList(members, MemberDetailResponseDto.class);
    }

    @Override
    public List<MemberDetailResponseDto> getAllStudent(Long courseId) {
        List<Member> members = memberRepository.findByTypeAndCourseId(MemberType.STUDENT, courseId);
        return modelMapperUtil.mapList(members, MemberDetailResponseDto.class);
    }

    @Override
    public List<MemberBasicInfoResponseDto> getAllInfo(Long courseId) {
        List<Member> members = memberRepository.findAllByCourseId(courseId);
        return modelMapperUtil.mapList(members, MemberBasicInfoResponseDto.class);
    }

    @Override
    public List<MemberBasicInfoResponseDto> getAllStudentInfo(Long courseId) {
        List<Member> members = memberRepository.findByTypeAndCourseId(MemberType.STUDENT, courseId);
        return modelMapperUtil.mapList(members, MemberBasicInfoResponseDto.class);
    }

    @Override
    public MemberBasicInfoResponseDto getInfoById(Long id) {
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "Id", id));
        return modelMapperUtil.mapOne(existMember, MemberBasicInfoResponseDto.class);
    }

    @Override
    public MemberDetailResponseDto getDetailById(Long id) {
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "Id", id));
        return modelMapperUtil.mapOne(existMember, MemberDetailResponseDto.class);
    }

    @Override
    public MemberDetailResponseDto add(MemberRequestDto member, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(member.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", member.getCourseId()));
        User user = userRepository.findById(member.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", member.getUserId()));

        Member newMember = modelMapperUtil.mapOne(member, Member.class);
        newMember.setCourse(course);
        newMember.setUser(user);
        newMember.setCreatedBy(currentUser);
        Member savedMember = memberRepository.save(newMember);
        return modelMapperUtil.mapOne(savedMember, MemberDetailResponseDto.class);
    }

    @Override
    public MemberDetailResponseDto update(Long id, MemberRequestDto member, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "Id", id));

        existMember.setGpa(member.getGpa());
        existMember.setType(member.getType());
        existMember.setStatus(member.getStatus());
        existMember.setUpdatedBy(currentUser);

        Member savedMember = memberRepository.save(existMember);
        return modelMapperUtil.mapOne(savedMember, MemberDetailResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "Id", id));

        existMember.setIsDeleted(!existMember.getIsDeleted());
        existMember.setUpdatedBy(currentUser);
        Member savedMember = memberRepository.save(existMember);
        return savedMember.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id) {
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "Id", id));
        memberRepository.delete(existMember);
        return true;
    }
}
