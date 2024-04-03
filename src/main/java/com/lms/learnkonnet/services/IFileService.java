package com.lms.learnkonnet.services;

import com.lms.learnkonnet.dtos.requests.file.FileRequestDto;
import com.lms.learnkonnet.dtos.requests.material.MaterialRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.file.FileResponseDto;
import com.lms.learnkonnet.dtos.responses.material.MaterialDetailResponseDto;

import java.util.List;

public interface IFileService {
    FileResponseDto getById(Long id);
    FileResponseDto add(FileRequestDto file);
    FileResponseDto update(Long id, FileRequestDto file);
    Boolean softDelete(Long id);
    Boolean delete(Long id);
}
