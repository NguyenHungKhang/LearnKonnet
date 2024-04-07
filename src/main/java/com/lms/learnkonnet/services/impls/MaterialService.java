package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.material.MaterialRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.material.MaterialDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.section.SectionDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.topic.TopicDetailResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.*;
import com.lms.learnkonnet.repositories.*;
import com.lms.learnkonnet.services.IFileService;
import com.lms.learnkonnet.services.IMaterialService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public class MaterialService implements IMaterialService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IMaterialRepository materialRepository;
    @Autowired
    private ISectionRepository sectionRepository;
    @Autowired
    private IFileRepository fileRepository;
    @Autowired
    private ICourseRepository courseRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public PageResponse<MaterialDetailResponseDto> getPageableListByCourse(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Material> materialsPage = materialRepository.findByCourseIdAndNameContaining(courseId, keyword, pageable);
        List<MaterialDetailResponseDto> materialsDtoPage = modelMapperUtil.mapList(materialsPage.getContent(), MaterialDetailResponseDto.class);

        return new PageResponse<>(
                materialsDtoPage,
                materialsPage.getNumber(),
                materialsPage.getSize(),
                materialsPage.getTotalElements(),
                materialsPage.getTotalPages(),
                materialsPage.isLast()
        );
    }

    @Override
    public PageResponse<MaterialDetailResponseDto> getPageableListBySection(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long sectionId) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<Material> materialsPage = materialRepository.findAllByMaterialSections_SectionIdAndNameContaining(sectionId, keyword, pageable);
        List<MaterialDetailResponseDto> materialsDtoPage = modelMapperUtil.mapList(materialsPage.getContent(), MaterialDetailResponseDto.class);

        return new PageResponse<>(
                materialsDtoPage,
                materialsPage.getNumber(),
                materialsPage.getSize(),
                materialsPage.getTotalElements(),
                materialsPage.getTotalPages(),
                materialsPage.isLast()
        );
    }

    @Override
    public List<MaterialDetailResponseDto> getAllByCourse(Long courseId) {
        List<Material> materials = materialRepository.findAllByCourseId(courseId);
        return modelMapperUtil.mapList(materials, MaterialDetailResponseDto.class);
    }

    @Override
    public List<MaterialDetailResponseDto> getAllBySection(Long sectionId) {
        List<Material> materials = materialRepository.findAllByMaterialSections_SectionId(sectionId);
        return modelMapperUtil.mapList(materials, MaterialDetailResponseDto.class);
    }

    @Override
    public MaterialDetailResponseDto getById(Long id) {
        Material material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material", "id", id));
        return modelMapperUtil.mapOne(material, MaterialDetailResponseDto.class);
    }

    @Override
    public MaterialDetailResponseDto add(MaterialRequestDto material, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        File file = fileRepository.findById(material.getFileId())
                .orElseThrow(() -> new ResourceNotFoundException("File", "Id", material.getFileId()));
        Course course = courseRepository.findById(material.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course", "Id", material.getCourseId()));

        Material newMaterial = modelMapperUtil.mapOne(material, Material.class);
        newMaterial.setFile(file);
        newMaterial.setCourse(course);
        newMaterial.setCreatedByMember(currentMember);
        Material savedMaterial = materialRepository.save(newMaterial);
        return modelMapperUtil.mapOne(savedMaterial, MaterialDetailResponseDto.class);
    }

    @Override
    public MaterialDetailResponseDto update(Long id, MaterialRequestDto material, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Material existMaterial = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material", "Id", id));
        existMaterial.setName(material.getName());
        existMaterial.setDesc(material.getDesc());
        existMaterial.setStartedAt(material.getStartedAt());
        existMaterial.setEndedAt(material.getEndedAt());
        existMaterial.setStatus(material.getStatus());
        existMaterial.setUpdatedByMember(currentMember);

        Material savedMaterial = materialRepository.save(existMaterial);
        return modelMapperUtil.mapOne(savedMaterial, MaterialDetailResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        Material existMaterial = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material", "Id", id));
        existMaterial.setIsDeleted(!existMaterial.getIsDeleted());
        existMaterial.setUpdatedByMember(currentMember);
        Material savedMaterial = materialRepository.save(existMaterial);
        return savedMaterial.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id) {
        Material existMaterial = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Material", "Id", id));
        materialRepository.delete(existMaterial);
        return true;
    }
}
