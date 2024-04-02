package com.lms.learnkonnet.dtos.responses.material;

import com.lms.learnkonnet.dtos.responses.file.FileResponseDto;
import com.lms.learnkonnet.models.enums.Status;

import java.sql.Timestamp;

public class MaterialDetailResponseDto {
    private Long id;
    private String slug;
    private Long courseId;
    private FileResponseDto file;
    private String name;
    private String desc;
    private Timestamp startedAt;
    private Timestamp endedAt;
    private Status status;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
