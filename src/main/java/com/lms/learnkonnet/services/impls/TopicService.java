package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.topic.TopicRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.course.CourseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;
import com.lms.learnkonnet.exceptions.ApiException;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Topic;
import com.lms.learnkonnet.models.User;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.models.enums.Status;
import com.lms.learnkonnet.repositories.ICourseRepository;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.repositories.ITopicRepository;
import com.lms.learnkonnet.repositories.IUserRepository;
import com.lms.learnkonnet.services.ITopicService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
public class TopicService implements ITopicService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ITopicRepository topicRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public PageResponse<TopicDetailResponseDto> getPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long currentUserId, Long courseId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, courseId);

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem chủ đề");

        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Topic> topicPage;
        if(currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT))
            topicPage = topicRepository.findByCourse_IdAndNameContainingAndStatusAndIsDeletedFalse(courseId, keyword, Status.AVAIABLE, pageable);
        else
            topicPage = topicRepository.findByCourse_IdAndNameContaining(courseId, keyword, pageable);
        List<TopicDetailResponseDto> topicsDtoPage = modelMapperUtil.mapList(topicPage.getContent(), TopicDetailResponseDto.class);

        return new PageResponse<>(
                topicsDtoPage,
                topicPage.getNumber(),
                topicPage.getSize(),
                topicPage.getTotalElements(),
                topicPage.getTotalPages(),
                topicPage.isLast()
        );
    }

    @Override
    public List<TopicDetailResponseDto> getAll(Long courseId, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", courseId));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, courseId);

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem chủ đề");

        List<Topic> topics;

        if(currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT))
            topics = topicRepository.findAllByCourse_IdAndStatusAndIsDeletedFalse(courseId, Status.AVAIABLE);
        else
            topics = topicRepository.findAllByCourse_Id(courseId);

        return modelMapperUtil.mapList(topics, TopicDetailResponseDto.class);
    }

    @Override
    public TopicDetailResponseDto getById(Long id, Long currentUserId) {

        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", id));
        Course course = courseRepository.findById(topic.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", topic.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, topic.getCourse().getId());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem chủ đề");

        Topic existTopic;

        if(currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT))
            existTopic = topicRepository.findByIdAndStatusAndIsDeletedFalse(id, Status.AVAIABLE)
                    .orElseThrow(() -> new ApiException("Học sinh không thể xem chủ đề này"));
        else
            existTopic = topic;

        return modelMapperUtil.mapOne(topic, TopicDetailResponseDto.class);
    }

    @Override
    public TopicBasicInfoResponseDto add(TopicRequestDto topic, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(topic.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", topic.getCourseId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, topic.getCourseId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền chỉnh sửa chủ đề");

        if(!topic.getStartedAt().before(topic.getEndedAt()))
            throw new ApiException("Thời gian bắt đầu phải trước thời gian kết thúc");

        if (currentTimestamp.before(topic.getStartedAt())) {
            topic.setStatus(Status.NOT_STARTED);
        } else if ((currentTimestamp.after(topic.getStartedAt()) || currentTimestamp.equals(topic.getStartedAt())) &&
                currentTimestamp.before(topic.getEndedAt()) &&
                !topic.getStatus().equals(Status.SUSPENDED)) {
            topic.setStatus(Status.AVAIABLE);
        } else if (currentTimestamp.after(topic.getEndedAt()) || currentTimestamp.equals((topic.getEndedAt()))){
            topic.setStatus(Status.ENDED);
        }

        Topic newTopic = modelMapperUtil.mapOne(topic, Topic.class);
        newTopic.setCourse(course);
        Topic savedTopic = topicRepository.save(newTopic);
        return modelMapperUtil.mapOne(savedTopic, TopicBasicInfoResponseDto.class);
    }

    @Override
    public TopicBasicInfoResponseDto update(Long id, TopicRequestDto topic, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Course course = courseRepository.findById(topic.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", topic.getCourseId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, topic.getCourseId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        Topic existTopic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Current topic", "Id", id));

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm chủ đề");

        if(!topic.getStartedAt().before(topic.getEndedAt()))
            throw new ApiException("Thời gian bắt đầu phải trước thời gian kết thúc");

        if (currentTimestamp.before(topic.getStartedAt())) {
            topic.setStatus(Status.NOT_STARTED);
        } else if ((currentTimestamp.after(topic.getStartedAt()) || currentTimestamp.equals(topic.getStartedAt())) &&
                currentTimestamp.before(topic.getEndedAt()) &&
                !topic.getStatus().equals(Status.SUSPENDED)) {
            topic.setStatus(Status.AVAIABLE);
        } else if (currentTimestamp.after(topic.getEndedAt()) || currentTimestamp.equals((topic.getEndedAt()))){
            topic.setStatus(Status.ENDED);
        }

        existTopic.setName(topic.getName());
        existTopic.setDesc(topic.getDesc());
        existTopic.setOrder(topic.getOrder());
        existTopic.setStartedAt(topic.getStartedAt());
        existTopic.setEndedAt(topic.getEndedAt());
        existTopic.setStatus(topic.getStatus());


        Topic savedTopic = topicRepository.save(existTopic);
        return modelMapperUtil.mapOne(savedTopic, TopicBasicInfoResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Topic existTopic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Current topic", "Id", id));
        Course course = courseRepository.findById(existTopic.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", existTopic.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, existTopic.getCourse().getId());


        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm chủ đề");

        existTopic.setIsDeleted(!existTopic.getIsDeleted());
        Topic savedTopic = topicRepository.save(existTopic);
        return savedTopic.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Topic existTopic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Current topic", "Id", id));
        Course course = courseRepository.findById(existTopic.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", existTopic.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, existTopic.getCourse().getId());


        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm chủ đề");


        topicRepository.delete(existTopic);
        return true;
    }
}
