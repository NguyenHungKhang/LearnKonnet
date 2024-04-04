package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.comment.CommentRequestDto;
import com.lms.learnkonnet.dtos.requests.course.CourseRequestDto;
import com.lms.learnkonnet.dtos.responses.comment.CommentResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.course.CourseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.course.CourseSumaryResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.User;
import com.lms.learnkonnet.repositories.ICourseRepository;
import com.lms.learnkonnet.repositories.IUserRepository;
import com.lms.learnkonnet.services.ICourseService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    public PageResponse<CourseSumaryResponseDto> getPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize) {
        return null;
    }
    @Override
    public PageResponse<CourseSumaryResponseDto> getPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long userId) {
        return null;
    }

    @Override
    public List<CourseSumaryResponseDto> getAll(Long userId) {
        return null;
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
