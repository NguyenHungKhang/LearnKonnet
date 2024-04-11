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
import com.lms.learnkonnet.exceptions.ApiException;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.repositories.*;
import com.lms.learnkonnet.services.IMemberService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
//        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
//        if (sortField == null || sortDir == null) sort = Sort.unsorted();
//        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
//        Page<Member> membersPage = memberRepository.findByCourse_IdAndUser_FullNameContainingOrUser_EmailContaining(courseId, keyword, pageable);
//        List<MemberBasicInfoResponseDto> membersDtoPage = modelMapperUtil.mapList(membersPage.getContent(), MemberBasicInfoResponseDto.class);
//
//        return new PageResponse<>(
//                membersDtoPage,
//                membersPage.getNumber(),
//                membersPage.getSize(),
//                membersPage.getTotalElements(),
//                membersPage.getTotalPages(),
//                membersPage.isLast()
//        );
        return null;
    }

    @Override
    public PageResponse<MemberDetailResponseDto> getDetailPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId) {
//        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
//        if (sortField == null || sortDir == null) sort = Sort.unsorted();
//        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
//        Page<Member> membersPage = memberRepository.findByCourse_IdAndUser_FullNameContainingOrUser_EmailContaining(courseId, keyword, pageable);
//        List<MemberDetailResponseDto> membersDtoPage = modelMapperUtil.mapList(membersPage.getContent(), MemberDetailResponseDto.class);
//
//        return new PageResponse<>(
//                membersDtoPage,
//                membersPage.getNumber(),
//                membersPage.getSize(),
//                membersPage.getTotalElements(),
//                membersPage.getTotalPages(),
//                membersPage.isLast()
//        );
        return null;
    }

    @Override
    public PageResponse<MemberBasicInfoResponseDto> getStudentInfoPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId) {
//
//
//        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
//        if (sortField == null || sortDir == null) sort = Sort.unsorted();
//        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
//        Page<Member> membersPage = memberRepository.findByTypeAndCourse_IdAndUser_FullNameContainingOrUser_EmailContaining(MemberType.STUDENT, courseId, keyword, pageable);
//        List<MemberBasicInfoResponseDto> membersDtoPage = modelMapperUtil.mapList(membersPage.getContent(), MemberBasicInfoResponseDto.class);
//
//        return new PageResponse<>(
//                membersDtoPage,
//                membersPage.getNumber(),
//                membersPage.getSize(),
//                membersPage.getTotalElements(),
//                membersPage.getTotalPages(),
//                membersPage.isLast()
//        );
        return null;
    }

    @Override
    public PageResponse<MemberBasicInfoResponseDto> getStudentInfoPageableListByMemberAndType(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long currentUserId, Long courseId, MemberStatus status, MemberType type) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        Optional<Member> member = memberRepository.findByUser_IdAndCourse_Id(currentUserId, courseId);

        if (!course.getUser().getId().equals(currentUserId) &&
                !(member.isPresent() &&
                        member.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có xem danh sách thành viên");

        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if (sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Member> membersPage = memberRepository.findByUserEmailContainingIgnoreCaseOrUserFullNameContainingIgnoreCaseAndTypeAndStatusAndCourse_Id(keyword, keyword, type, status, courseId, pageable);
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
//        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
//        if (sortField == null || sortDir == null) sort = Sort.unsorted();
//        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
//        Page<Member> membersPage = memberRepository.findByTypeAndCourse_IdAndUser_FullNameContainingOrUser_EmailContaining(MemberType.STUDENT, courseId, keyword, pageable);
//        List<MemberDetailResponseDto> membersDtoPage = modelMapperUtil.mapList(membersPage.getContent(), MemberDetailResponseDto.class);
//
//        return new PageResponse<>(
//                membersDtoPage,
//                membersPage.getNumber(),
//                membersPage.getSize(),
//                membersPage.getTotalElements(),
//                membersPage.getTotalPages(),
//                membersPage.isLast()
//        );
        return null;
    }

    @Override
    public List<MemberDetailResponseDto> getAll(Long courseId) {
//        List<Member> members = memberRepository.findAllByCourseId(courseId);
//        return modelMapperUtil.mapList(members, MemberDetailResponseDto.class);
        return null;
    }

    @Override
    public List<MemberDetailResponseDto> getAllStudent(Long courseId) {
//        List<Member> members = memberRepository.findByTypeAndCourseId(MemberType.STUDENT, courseId);
//        return modelMapperUtil.mapList(members, MemberDetailResponseDto.class);
        return null;
    }

    @Override
    public List<MemberBasicInfoResponseDto> getAllInfo(Long courseId) {
//        List<Member> members = memberRepository.findAllByCourseId(courseId);
//        return modelMapperUtil.mapList(members, MemberBasicInfoResponseDto.class);
        return null;
    }

    @Override
    public List<MemberBasicInfoResponseDto> getAllStudentInfo(Long courseId, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        Optional<Member> member = memberRepository.findByUser_IdAndCourse_Id(currentUserId, courseId);

        if (!course.getUser().getId().equals(currentUserId) &&
                !(member.isPresent() &&
                        member.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không thể xem danh sách thành viên");

        List<Member> members = memberRepository.findByTypeAndStatusAndCourseId(MemberType.STUDENT, MemberStatus.ACTIVED, courseId);
        return modelMapperUtil.mapList(members, MemberBasicInfoResponseDto.class);
    }

    @Override
    public MemberBasicInfoResponseDto getInfoById(Long id, Long currentUserId) {
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "Id", id));
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(existMember.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", existMember.getCourse().getId()));
        Optional<Member> member = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(member.isPresent() &&
                        member.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không thể xem thành viên");

        return modelMapperUtil.mapOne(existMember, MemberBasicInfoResponseDto.class);
    }

    @Override
    public MemberDetailResponseDto getDetailById(Long id, Long currentUserId) {
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "Id", id));
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(existMember.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", existMember.getCourse().getId()));
        Optional<Member> member = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(member.isPresent() &&
                        member.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không thể xem thành viên");

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
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, member.getCourseId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !member.getUserId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm thành viên");

        if (course.getUser().getId().equals(member.getUserId()))
            throw new ApiException("Chủ sở hữu lớp học không thể thêm bản thân thành thành viên");

        Optional<Member> existMember = memberRepository.findByUser_IdAndCourse_Id(member.getUserId(), member.getCourseId());
        if (existMember.isPresent())
            throw new ApiException("Người dùng đã là thành viên lớp học");

        if (member.getUserId().equals(currentUserId))
            member.setStatus(MemberStatus.WAIT);
        else
            member.setStatus(MemberStatus.INVITED);

        Member newMember = modelMapperUtil.mapOne(member, Member.class);
        newMember.setCourse(course);
        newMember.setUser(user);
        Member savedMember = memberRepository.save(newMember);
        return modelMapperUtil.mapOne(savedMember, MemberDetailResponseDto.class);
    }

    @Override
    public MemberDetailResponseDto update(Long id, MemberRequestDto member, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(member.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", member.getCourseId()));
        User user = userRepository.findById(member.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", member.getUserId()));
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "Id", id));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, member.getCourseId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(member.getUserId().equals(currentUserId) &&
                        currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền chỉnh sửa thành viên");

        if (currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.TEACHER)) {
            if (!existMember.getUser().getId().equals(currentUserId) && existMember.getType().equals(MemberType.TEACHER))
                throw new ApiException("Giáo viên không thể chỉnh sửa giáo viên khác");
            existMember.setGpa(member.getGpa());
            existMember.setStatus(member.getStatus());
        } else if (currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT) && member.getUserId().equals(currentUserId)) {
            existMember.setStatus(member.getStatus());
        } else if (course.getUser().getId().equals(currentUserId)) {
            existMember.setGpa(member.getGpa());
            existMember.setType(member.getType());
            existMember.setStatus(member.getStatus());
        }

        Member savedMember = memberRepository.save(existMember);
        return modelMapperUtil.mapOne(savedMember, MemberDetailResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "Id", id));
        Course course = courseRepository.findById(existMember.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", existMember.getCourse().getId()));
        User user = userRepository.findById(existMember.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", existMember.getUser().getId()));

        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, existMember.getCourse().getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(existMember.getUser().getId().equals(currentUserId) &&
                        currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xóa thành viên");

        existMember.setIsDeleted(!existMember.getIsDeleted());
        Member savedMember = memberRepository.save(existMember);
        return savedMember.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Member existMember = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "Id", id));
        Course course = courseRepository.findById(existMember.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", existMember.getCourse().getId()));
        User user = userRepository.findById(existMember.getUser().getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", existMember.getUser().getId()));

        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, existMember.getCourse().getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(existMember.getUser().getId().equals(currentUserId) &&
                        currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xóa thành viên");


        memberRepository.delete(existMember);
        return true;
    }
}
