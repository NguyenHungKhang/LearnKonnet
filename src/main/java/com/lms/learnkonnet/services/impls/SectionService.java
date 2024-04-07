package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.section.SectionRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.section.SectionDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicBasicInfoResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.repositories.ICourseRepository;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.repositories.ISectionRepository;
import com.lms.learnkonnet.repositories.ITopicRepository;
import com.lms.learnkonnet.services.ISectionService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SectionService implements ISectionService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private ITopicRepository topicRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private ISectionRepository sectionRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public PageResponse<SectionDetailResponseDto> getPageableListByTopic(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long topicId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Section> sectionsPage = sectionRepository.findByTopicIdAndNameContaining(topicId, keyword, pageable);
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
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Section> sectionsPage = sectionRepository.findByCourseIdAndNameContaining(courseId, keyword, pageable);
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
    public List<SectionDetailResponseDto> getAllByTopic(Long topicId) {
        List<Section> sections = sectionRepository.findAllByTopicId(topicId);
        return modelMapperUtil.mapList(sections, SectionDetailResponseDto.class);
    }

    @Override
    public List<SectionDetailResponseDto> getAllByCourse(Long courseId) {
        List<Section> sections = sectionRepository.findAllByCourseId(courseId);
        return modelMapperUtil.mapList(sections, SectionDetailResponseDto.class);
    }

    @Override
    public SectionDetailResponseDto getById(Long id) {
        Section section = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section", "id", id));
        return modelMapperUtil.mapOne(section, SectionDetailResponseDto.class);
    }

    @Override
    public SectionDetailResponseDto add(SectionRequestDto section, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Topic topic = topicRepository.findById(section.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("File", "Id", section.getTopicId()));
        Course course = courseRepository.findById(section.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", section.getCourseId()));

        Section newSection = modelMapperUtil.mapOne(section, Section.class);
        newSection.setTopic(topic);
        newSection.setCourse(course);
        newSection.setCreatedByMember(currentMember);
        Section savedSection = sectionRepository.save(newSection);
        return modelMapperUtil.mapOne(savedSection, SectionDetailResponseDto.class);
    }

    @Override
    public SectionDetailResponseDto update(Long id, SectionRequestDto section, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Section existSection = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section", "Id", id));
        Topic topic = topicRepository.findById(section.getTopicId())
                .orElseThrow(() -> new ResourceNotFoundException("File", "Id", section.getTopicId()));

        existSection.setName(section.getName());
        existSection.setDesc(section.getDesc());
        existSection.setOrder(section.getOrder());
        existSection.setTopic(topic);
        existSection.setStartedAt(section.getStartedAt());
        existSection.setEndedAt(section.getEndedAt());
        existSection.setStatus(section.getStatus());
        existSection.setUpdatedByMember(currentMember);

        Section savedSection = sectionRepository.save(existSection);
        return modelMapperUtil.mapOne(savedSection, SectionDetailResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Section existSection = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section", "Id", id));
        existSection.setIsDeleted(!existSection.getIsDeleted());
        existSection.setUpdatedByMember(currentMember);
        Section savedSection = sectionRepository.save(existSection);
        return savedSection.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id) {
        Section existSection = sectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Section", "Id", id));
        sectionRepository.delete(existSection);
        return true;
    }
}
