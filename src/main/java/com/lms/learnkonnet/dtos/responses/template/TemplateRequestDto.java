package com.lms.learnkonnet.dtos.responses.template;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TemplateRequestDto {
    private Long quizId;
    private String templateCode;
    private String questionPattern;
    private String answerPattern;
}
