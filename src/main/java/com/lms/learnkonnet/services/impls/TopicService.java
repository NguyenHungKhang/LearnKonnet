package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.topic.TopicRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.course.CourseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Topic;
import com.lms.learnkonnet.models.User;
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

import java.util.List;

@Service
public class TopicService implements ITopicService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private ITopicRepository topicRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public PageResponse<TopicDetailResponseDto> getPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Topic> topicPage = topicRepository.findByCourseIdAndNameContaining(courseId, keyword, pageable);
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
    public List<TopicDetailResponseDto> getAll(Long courseId) {
        List<Topic> topics = topicRepository.findAllByCourseId(courseId);
        return modelMapperUtil.mapList(topics, TopicDetailResponseDto.class);
    }

    @Override
    public TopicDetailResponseDto getById(Long id) {
        Topic topic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "id", id));
        return modelMapperUtil.mapOne(topic, TopicDetailResponseDto.class);
    }

    @Override
    public TopicBasicInfoResponseDto add(TopicRequestDto topic, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Course course = courseRepository.findById(topic.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", topic.getCourseId()));

        Topic newTopic = modelMapperUtil.mapOne(topic, Topic.class);
        newTopic.setCreatedByMember(currentMember);
        newTopic.setCourse(course);
        Topic savedTopic = topicRepository.save(newTopic);
        return modelMapperUtil.mapOne(savedTopic, TopicBasicInfoResponseDto.class);
    }

    @Override
    public TopicBasicInfoResponseDto update(Long id, TopicRequestDto topic, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Topic existTopic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Current topic", "Id", id));
        existTopic.setName(topic.getName());
        existTopic.setDesc(topic.getDesc());
        existTopic.setOrder(topic.getOrder());
        existTopic.setStartedAt(topic.getStartedAt());
        existTopic.setEndedAt(topic.getEndedAt());
        existTopic.setStatus(topic.getStatus());
        existTopic.setUpdatedByMember(currentMember);

        Topic savedTopic = topicRepository.save(existTopic);
        return modelMapperUtil.mapOne(savedTopic, TopicBasicInfoResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Topic existTopic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Current topic", "Id", id));
        existTopic.setIsDeleted(!existTopic.getIsDeleted());
        existTopic.setUpdatedByMember(currentMember);
        Topic savedTopic = topicRepository.save(existTopic);
        return savedTopic.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id) {
        Topic existTopic = topicRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Topic", "Id", id));
        topicRepository.delete(existTopic);
        return true;
    }
}
