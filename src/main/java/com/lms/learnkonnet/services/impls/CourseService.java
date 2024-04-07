package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.comment.CommentRequestDto;
import com.lms.learnkonnet.dtos.requests.course.CourseRequestDto;
import com.lms.learnkonnet.dtos.responses.comment.CommentResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.course.CourseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.course.CourseSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserDetailResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.User;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.repositories.ICourseRepository;
import com.lms.learnkonnet.repositories.IUserRepository;
import com.lms.learnkonnet.services.ICourseService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService implements ICourseService {
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public PageResponse<CourseSumaryResponseDto> getAllPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Course> coursesPage = courseRepository.findByNameContaining(keyword, pageable);
        List<CourseSumaryResponseDto> coursesDtoPage = modelMapperUtil.mapList(coursesPage.getContent(), CourseSumaryResponseDto.class);

        return new PageResponse<>(
                coursesDtoPage,
                coursesPage.getNumber(),
                coursesPage.getSize(),
                coursesPage.getTotalElements(),
                coursesPage.getTotalPages(),
                coursesPage.isLast()
        );
    }
    @Override
    public PageResponse<CourseSumaryResponseDto> getAllPageableListByUser(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long userId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Course> coursesPage = courseRepository.findByUserIdOrMembersUserIdAndMembersStatusNotAndNameContaining(userId, userId, MemberStatus.BANNED, keyword, pageable);
        List<CourseSumaryResponseDto> coursesDtoPage = modelMapperUtil.mapList(coursesPage.getContent(), CourseSumaryResponseDto.class);

        return new PageResponse<>(
                coursesDtoPage,
                coursesPage.getNumber(),
                coursesPage.getSize(),
                coursesPage.getTotalElements(),
                coursesPage.getTotalPages(),
                coursesPage.isLast()
        );
    }

    @Override
    public PageResponse<CourseSumaryResponseDto> getAllPageableListByOwner(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long userId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Course> coursesPage = courseRepository.findByUserIdAndNameContaining(userId, keyword, pageable);
        List<CourseSumaryResponseDto> coursesDtoPage = modelMapperUtil.mapList(coursesPage.getContent(), CourseSumaryResponseDto.class);

        return new PageResponse<>(
                coursesDtoPage,
                coursesPage.getNumber(),
                coursesPage.getSize(),
                coursesPage.getTotalElements(),
                coursesPage.getTotalPages(),
                coursesPage.isLast()
        );
    }

    @Override
    public List<CourseSumaryResponseDto> getAll(Long userId) {
        List<Course> courses = courseRepository.findAllByUserId(userId);
        return modelMapperUtil.mapList(courses, CourseSumaryResponseDto.class);
    }

    @Override
    public CourseSumaryResponseDto getSumaryById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Code", id));
        return modelMapperUtil.mapOne(course, CourseSumaryResponseDto.class);
    }

    @Override
    public CourseDetailResponseDto getById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Code", id));
        return modelMapperUtil.mapOne(course, CourseDetailResponseDto.class);
    }

    @Override
    public CourseDetailResponseDto add(CourseRequestDto course, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course newCourse = modelMapperUtil.mapOne(course, Course.class);
        newCourse.setUser(currentUser);
        newCourse.setCreatedBy(currentUser);
        Course savedCourse = courseRepository.save(newCourse);
        return modelMapperUtil.mapOne(savedCourse, CourseDetailResponseDto.class);
    }

    @Override
    public CourseDetailResponseDto update(Long id, CourseRequestDto course, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course existCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Current course", "Id", id));

        existCourse.setName(course.getName());
        existCourse.setDesc(course.getDesc());
        existCourse.setCover(course.getCover());
        existCourse.setIsApproveStudents(course.getIsApproveStudents());
        existCourse.setIsPreventStudents(course.getIsPreventStudents());
        existCourse.setIsShowScore(course.getIsShowScore());
        existCourse.setIsStudentAllowPost(course.getIsStudentAllowPost());
        existCourse.setIsStudentAllowComment(course.getIsStudentAllowComment());
        existCourse.setIsStudentAllowComment(course.getIsStudentAllowComment());
        existCourse.setStartedAt(course.getStartedAt());
        existCourse.setEndedAt(course.getEndedAt());
        existCourse.setStatus(course.getStatus());
        existCourse.setUpdatedBy(currentUser);

        Course savedCourse = courseRepository.save(existCourse);
        return modelMapperUtil.mapOne(existCourse, CourseDetailResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentUserId) {
        Course existCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", id));
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        existCourse.setIsDeleted(!existCourse.getIsDeleted());
        existCourse.setUpdatedBy(currentUser);
        Course saveddCourse = courseRepository.save(existCourse);
        return saveddCourse.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id) {
        Course existCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", id));
        courseRepository.delete(existCourse);
        return true;
    }
}
