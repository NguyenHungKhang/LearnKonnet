package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.file.FileRequestDto;
import com.lms.learnkonnet.dtos.responses.file.FileResponseDto;
import com.lms.learnkonnet.services.IFileService;

public class FileService implements IFileService {
    @Override
    public FileResponseDto getById(Long id) {
        return null;
    }

    @Override
    public FileResponseDto add(FileRequestDto file) {
        return null;
    }

    @Override
    public FileResponseDto update(Long id, FileRequestDto file) {
        return null;
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
