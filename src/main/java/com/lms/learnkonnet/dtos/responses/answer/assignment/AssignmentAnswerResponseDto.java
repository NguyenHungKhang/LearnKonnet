package com.lms.learnkonnet.dtos.responses.answer.assignment;

import com.lms.learnkonnet.dtos.responses.file.FileResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignmentAnswerResponseDto {
    private Long id;
    private Long answerId;
    private FileResponseDto file;
    private String content;
    private Integer order;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
