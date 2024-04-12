package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.file.FileRequestDto;
import com.lms.learnkonnet.dtos.responses.file.FileResponseDto;
import com.lms.learnkonnet.dtos.responses.material.MaterialDetailResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.File;
import com.lms.learnkonnet.models.Material;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.repositories.IFileRepository;
import com.lms.learnkonnet.repositories.IMemberRepository;
import com.lms.learnkonnet.services.IFileService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService implements IFileService {
    @Autowired
    private IMemberRepository memberRepository;
    @Autowired
    private IFileRepository fileRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public FileResponseDto getById(Long id) {
        File existFile = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File", "Id", id));
        return modelMapperUtil.mapOne(existFile, FileResponseDto.class);
    }

    @Override
    public FileResponseDto add(FileRequestDto file, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));

        File newFile = modelMapperUtil.mapOne(file, File.class);
        File savedFile = fileRepository.save(newFile);
        return modelMapperUtil.mapOne(savedFile, FileResponseDto.class);
    }

    @Override
    public FileResponseDto update(Long id, FileRequestDto file, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        File existFile = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File", "Id", id));

        existFile.setName(file.getName());
//        existFile.setType(file.getType());
        existFile.setUrl(file.getUrl());


        File savedFile = fileRepository.save(existFile);
        return modelMapperUtil.mapOne(savedFile, FileResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentMemberId) {
        Member currentMember = memberRepository.findById(currentMemberId)
                .orElseThrow(() -> new ResourceNotFoundException("Current member", "Id", currentMemberId));
        File existFile = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File", "Id", id));

        existFile.setIsDeleted(!existFile.getIsDeleted());
        File savedFile = fileRepository.save(existFile);
        return savedFile.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id) {
        File existFile = fileRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("File", "Id", id));
        fileRepository.delete(existFile);
        return true;
    }


}
