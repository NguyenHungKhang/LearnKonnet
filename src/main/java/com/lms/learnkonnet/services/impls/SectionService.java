package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.section.SectionRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.section.SectionDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;
import com.lms.learnkonnet.exceptions.ApiException;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import com.lms.learnkonnet.models.enums.Status;
import com.lms.learnkonnet.repositories.*;
import com.lms.learnkonnet.services.ISectionService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import com.lms.learnkonnet.utils.SlugUtils;
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
public class SectionService implements ISectionService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ITopicRepository topicRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private ISectionRepository sectionRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public PageResponse<SectionDetailResponseDto> getPageableListByTopic(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long currentUserId, Long topicId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "Id", topicId));
        Course course = courseRepository.findById(topic.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", topic.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem danh sách mục");

        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Section> sectionsPage;
        if(currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT))
            sectionsPage = sectionRepository.findSuperSectionsByTopicIdAndAllStatusAndAllIsDeleted(topicId, Status.AVAIABLE, Status.AVAIABLE, Status.AVAIABLE, Status.AVAIABLE, Status.AVAIABLE, keyword, pageable);
        else
            sectionsPage = sectionRepository.findByTopic_IdAndNameContaining(topicId, keyword, pageable);

        List<SectionDetailResponseDto> sectionsDtoPage = modelMapperUtil.mapList(sectionsPage.getContent(), SectionDetailResponseDto.class);

        return new PageResponse<>(
                sectionsDtoPage,
                sectionsPage.getNumber(),
                sectionsPage.getSize(),
                sectionsPage.getTotalElements(),
                sectionsPage.getTotalPages(),
                sectionsPage.isLast()
        );
    }

    @Override
    public PageResponse<SectionDetailResponseDto> getPageableListByCourse(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId) {
//        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
//        if(sortField == null || sortDir == null) sort = Sort.unsorted();
//        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
//        Page<Section> sectionsPage = sectionRepository.findByCourse_IdAndNameContaining(courseId, keyword, pageable);
//        List<SectionDetailResponseDto> sectionsDtoPage = modelMapperUtil.mapList(sectionsPage.getContent(), SectionDetailResponseDto.class);
//
//        return new PageResponse<>(
//                sectionsDtoPage,
//                sectionsPage.getNumber(),
//                sectionsPage.getSize(),
//                sectionsPage.getTotalElements(),
//                sectionsPage.getTotalPages(),
//                sectionsPage.isLast()
//        );
        return null;
    }

    @Override
    public List<SectionDetailResponseDto> getAllByTopic(Long topicId, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "Id", topicId));
        Course course = courseRepository.findById(topic.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", topic.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem danh sách mục");

        List<Section> sections;
        if(currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT))
            sections = sectionRepository.findSuperSectionsByTopicIdAndAllStatusAndAllIsDeleted(topicId, Status.AVAIABLE, Status.AVAIABLE, Status.AVAIABLE, Status.AVAIABLE, Status.AVAIABLE);
        else
            sections = sectionRepository.findAllByTopic_Id(topicId);
        return modelMapperUtil.mapList(sections, SectionDetailResponseDto.class);
    }

    @Override
    public List<SectionDetailResponseDto> getAllByCourse(Long courseId) {
        List<Section> sections = sectionRepository.findAllByCourse_Id(courseId);
        return modelMapperUtil.mapList(sections, SectionDetailResponseDto.class);
    }

    @Override
    public SectionDetailResponseDto getById(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section", "Id", id));
        Topic topic = topicRepository.findById(section.getTopic().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "Id", section.getTopic().getId()));
        Course course = courseRepository.findById(section.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", section.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xem danh sách mục");

        Section existSection;
        if(currentUserMember.isPresent() && currentUserMember.get().getType().equals(MemberType.STUDENT))
            existSection = sectionRepository.findByIdAndStatusAndIsDeletedFalse(id, Status.AVAIABLE)
                    .orElseThrow(() -> new ApiException("Học sinh không thể xem mục này"));
        else
            existSection = section;

        return modelMapperUtil.mapOne(section, SectionDetailResponseDto.class);
    }

    @Override
    public SectionDetailResponseDto add(SectionRequestDto section, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Topic topic = topicRepository.findById(section.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "Id", section.getTopicId()));
        Course course = courseRepository.findById(section.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", section.getCourseId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, section.getCourseId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền thêm mục");

        if(!section.getStartedAt().before(section.getEndedAt()))
            throw new ApiException("Thời gian bắt đầu phải trước thời gian kết thúc");

        if (currentTimestamp.before(section.getStartedAt())) {
            section.setStatus(Status.NOT_STARTED);
        } else if ((currentTimestamp.after(section.getStartedAt()) || currentTimestamp.equals(section.getStartedAt())) &&
                currentTimestamp.before(section.getEndedAt()) &&
                !section.getStatus().equals(Status.SUSPENDED)) {
            section.setStatus(Status.AVAIABLE);
        } else if (currentTimestamp.after(section.getEndedAt()) || currentTimestamp.equals((section.getEndedAt()))){
            section.setStatus(Status.ENDED);
        }


        Section newSection = modelMapperUtil.mapOne(section, Section.class);
        newSection.setTopic(topic);
        newSection.setCourse(course);
        newSection.setSlug(SlugUtils.generateSlug(newSection.getName()));
        Section savedSection = sectionRepository.save(newSection);
        return modelMapperUtil.mapOne(savedSection, SectionDetailResponseDto.class);
    }

    @Override
    public SectionDetailResponseDto update(Long id, SectionRequestDto section, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Topic topic = topicRepository.findById(section.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "Id", section.getTopicId()));
        Course course = courseRepository.findById(section.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", section.getCourseId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, section.getCourseId());
        Section existSection = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section", "Id", id));
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền chỉnh sửa mục");

        if(!section.getStartedAt().before(section.getEndedAt()))
            throw new ApiException("Thời gian bắt đầu phải trước thời gian kết thúc");

        if (currentTimestamp.before(section.getStartedAt())) {
            section.setStatus(Status.NOT_STARTED);
        } else if ((currentTimestamp.after(section.getStartedAt()) || currentTimestamp.equals(section.getStartedAt())) &&
                currentTimestamp.before(section.getEndedAt()) &&
                !section.getStatus().equals(Status.SUSPENDED)) {
            section.setStatus(Status.AVAIABLE);
        } else if (currentTimestamp.after(section.getEndedAt()) || currentTimestamp.equals((section.getEndedAt()))){
            section.setStatus(Status.ENDED);
        }

        existSection.setName(section.getName());
        existSection.setDesc(section.getDesc());
        existSection.setOrder(section.getOrder());
        existSection.setTopic(topic);
        existSection.setStartedAt(section.getStartedAt());
        existSection.setEndedAt(section.getEndedAt());
        existSection.setStatus(section.getStatus());
        Section savedSection = sectionRepository.save(existSection);
        return modelMapperUtil.mapOne(savedSection, SectionDetailResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Section existSection = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section", "Id", id));
        Topic topic = topicRepository.findById(existSection.getTopic().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "Id", existSection.getTopic().getId()));
        Course course = courseRepository.findById(existSection.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", existSection.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xóa mục");

        existSection.setIsDeleted(!existSection.getIsDeleted());
        Section savedSection = sectionRepository.save(existSection);
        return savedSection.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id, Long currentUserId) {
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        Section existSection = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section", "Id", id));
        Topic topic = topicRepository.findById(existSection.getTopic().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "Id", existSection.getTopic().getId()));
        Course course = courseRepository.findById(existSection.getCourse().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", existSection.getCourse().getId()));
        Optional<Member> currentUserMember = memberRepository.findByUser_IdAndCourse_Id(currentUserId, course.getId());
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());

        if (!course.getUser().getId().equals(currentUserId) &&
                !(currentUserMember.isPresent() &&
                        currentUserMember.get().getType().equals(MemberType.TEACHER) &&
                        currentUserMember.get().getStatus().equals(MemberStatus.ACTIVED)))
            throw new ApiException("Người dùng không có quyền xóa mục");

        sectionRepository.delete(existSection);
        return true;
    }
}
