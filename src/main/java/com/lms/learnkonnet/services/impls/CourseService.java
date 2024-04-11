package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.comment.CommentRequestDto;
import com.lms.learnkonnet.dtos.requests.course.CourseRequestDto;
import com.lms.learnkonnet.dtos.responses.comment.CommentResponseDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.course.CourseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.course.CourseSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserDetailResponseDto;
import com.lms.learnkonnet.exceptions.ApiException;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.User;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.models.enums.Status;
import com.lms.learnkonnet.repositories.ICourseRepository;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.repositories.IUserRepository;
import com.lms.learnkonnet.services.ICourseService;
import com.lms.learnkonnet.services.IMemberService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import com.lms.learnkonnet.utils.RandomCodeUtils;
import com.lms.learnkonnet.utils.SlugUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CourseService implements ICourseService {
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private IMemberRepository memberRepository;
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
    public PageResponse<CourseSumaryResponseDto> getAllPageableListByStatusMember(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long userId, MemberStatus status) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Course> coursesPage = courseRepository.findByMembersUser_IdAndMembers_StatusAndNameContaining(userId, status, keyword, pageable);
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
    public PageResponse<CourseSumaryResponseDto> getAllPageableListByStatusMemberAndMemberType(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long userId, MemberStatus status, MemberType type) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Course> coursesPage = courseRepository.findByMembersUser_IdAndMembers_StatusAndMembers_TypeAndNameContaining(userId, status, type, keyword, pageable);
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
        Page<Course> coursesPage = courseRepository.findByUser_IdAndNameContaining(userId, keyword, pageable);
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
        List<Course> courses = courseRepository.findAllByUser_Id(userId);
        return modelMapperUtil.mapList(courses, CourseSumaryResponseDto.class);
    }

    @Override
    public CourseSumaryResponseDto getSumaryById(Long id, Long currentUserId) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Code", id));

        if (course.getUser().getId().equals(currentUserId)) {
            return modelMapperUtil.mapOne(course, CourseSumaryResponseDto.class);
        } else {
            Member member = memberRepository.findByUserIdAndCourseId(currentUserId, course.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Member", "User and Course", currentUserId + " - " + id));
            return modelMapperUtil.mapOne(course, CourseSumaryResponseDto.class);
        }
    }

    @Override
    public CourseDetailResponseDto getById(Long id, Long currentUserId) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Code", id));

        if (course.getUser().getId().equals(currentUserId)) {
            return modelMapperUtil.mapOne(course, CourseDetailResponseDto.class);
        } else {
            Member member = memberRepository.findByUserIdAndCourseId(currentUserId, course.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Member", "User and Course", currentUserId + " - " + id));
            return modelMapperUtil.mapOne(course, CourseDetailResponseDto.class);
        }
    }


    @Override
    public CourseDetailResponseDto add(CourseRequestDto course, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if(!course.getUserId().equals(currentUserId))
            throw new ApiException("Owner user info are not correct");

        if(!course.getStartedAt().before(course.getEndedAt()))
            throw new ApiException("Start time must before end time");

        if (currentTimestamp.before(course.getStartedAt())) {
            course.setStatus(Status.NOT_STARTED);
        } else if ((currentTimestamp.after(course.getStartedAt()) || currentTimestamp.equals(course.getStartedAt())) &&
                currentTimestamp.before(course.getEndedAt()) &&
                !course.getStatus().equals(Status.SUSPENDED)) {
            course.setStatus(Status.AVAIABLE);
        } else if (currentTimestamp.after(course.getEndedAt()) || currentTimestamp.equals((course.getEndedAt()))){
            course.setStatus(Status.ENDED);
        }

        String codeTemp;
        do {
            codeTemp = RandomCodeUtils.generateUniqueAccessKey();
        } while (courseRepository.findByCode(codeTemp).isEmpty());
        course.setCode(codeTemp);

        Course newCourse = modelMapperUtil.mapOne(course, Course.class);
        newCourse.setUser(currentUser);
        newCourse.setSlug(SlugUtils.generateSlug(newCourse.getName()));
        Course savedCourse = courseRepository.save(newCourse);
        return modelMapperUtil.mapOne(savedCourse, CourseDetailResponseDto.class);
    }

    @Override
    public CourseDetailResponseDto update(Long id, CourseRequestDto course, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course existCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Current course", "Id", id));
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if(!currentUser.getId().equals(existCourse.getUser().getId()))
            throw new ApiException("User cannot modify this course");

        if(!course.getStartedAt().before(course.getEndedAt()))
            throw new ApiException("Start time must before end time");

        if (currentTimestamp.before(course.getStartedAt())) {
            course.setStatus(Status.NOT_STARTED);
        } else if ((currentTimestamp.after(course.getStartedAt()) || currentTimestamp.equals(course.getStartedAt())) &&
                currentTimestamp.before(course.getEndedAt()) &&
                !course.getStatus().equals(Status.SUSPENDED)) {
            course.setStatus(Status.AVAIABLE);
        } else if (currentTimestamp.after(course.getEndedAt()) || currentTimestamp.equals((course.getEndedAt()))){
            course.setStatus(Status.ENDED);
        }

        existCourse.setName(course.getName());
        existCourse.setDesc(course.getDesc());
        existCourse.setCover(course.getCover());
        existCourse.setIsApproveStudents(course.getIsApproveStudents());
        existCourse.setIsPreventStudents(course.getIsPreventStudents());
        existCourse.setIsShowScore(course.getIsShowScore());
        existCourse.setIsStudentAllowPost(course.getIsStudentAllowPost());
        existCourse.setIsStudentAllowComment(course.getIsStudentAllowComment());
        existCourse.setIsStudentAllowComment(course.getIsStudentAllowComment());
        existCourse.setSlug(SlugUtils.generateSlug(course.getName()));
        existCourse.setStartedAt(course.getStartedAt());
        existCourse.setEndedAt(course.getEndedAt());
        existCourse.setStatus(course.getStatus());

        Course savedCourse = courseRepository.save(existCourse);
        return modelMapperUtil.mapOne(existCourse, CourseDetailResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentUserId) {
        Course existCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", id));
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));

        if(!currentUser.getId().equals(existCourse.getUser().getId()))
            throw new ApiException("User cannot delete this course");

        existCourse.setIsDeleted(!existCourse.getIsDeleted());
        Course saveddCourse = courseRepository.save(existCourse);
        return saveddCourse.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id, Long currentUserId) {
        Course existCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", id));
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));

        if(!currentUser.getId().equals(existCourse.getUser().getId()))
            throw new ApiException("User cannot delete this course");
        courseRepository.deleteById(id);
        return true;
    }
}
