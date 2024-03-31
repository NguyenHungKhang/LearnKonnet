package com.lms.learnkonnet.dtos.requests;

import com.lms.learnkonnet.models.Quiz;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
