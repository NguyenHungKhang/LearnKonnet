package com.lms.learnkonnet.dtos.responses.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TemplateResponseDto {
    private Long id;
    private Long quizId;
    private String templateCode;
    private String questionPattern;
    private String answerPattern;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
