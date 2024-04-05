package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.material.MaterialRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.material.MaterialDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.section.SectionDetailResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.Material;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Section;
import com.lms.learnkonnet.repositories.IMaterialRepository;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.repositories.ISectionRepository;
import com.lms.learnkonnet.repositories.ITopicRepository;
import com.lms.learnkonnet.services.IFileService;
import com.lms.learnkonnet.services.IMaterialService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MaterialService implements IMaterialService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IMaterialRepository materialRepository;
    @Autowired
    private ISectionRepository sectionRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public PageResponse<MaterialDetailResponseDto> getPageableListByCourse(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long courseId) {
        return null;
    }

    @Override
    public PageResponse<MaterialDetailResponseDto> getPageableListBySection(String keyword, String sortField, String sortDir, int pageNum, int pageSize, Long sectionId) {
        return null;
    }

    @Override
    public List<MaterialDetailResponseDto> getAllByCourse(Long courseId) {
        return null;
    }

    @Override
    public List<MaterialDetailResponseDto> getAllBySection(Long sectionId) {
        return null;
    }

    @Override
    public MaterialDetailResponseDto getById(Long id) {
        return null;
    }

    @Override
    public MaterialDetailResponseDto add(MaterialRequestDto material, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));

        Material newMaterial = modelMapperUtil.mapOne(material, Material.class);
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
//        existMaterial.setFile(material.getFile());
        existMaterial.setStartedAt(material.getStartedAt());
        existMaterial.setEndedAt(material.getEndedAt());
        existMaterial.setStatus(material.getStatus());
        existMaterial.setUpdatedByMember(currentMember);

        Material savedMaterial = materialRepository.save(existMaterial);
        return modelMapperUtil.mapOne(savedMaterial, MaterialDetailResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id) {
        return null;
    }

    @Override
    public Boolean delete(Long id) {
        return null;
    }
}
