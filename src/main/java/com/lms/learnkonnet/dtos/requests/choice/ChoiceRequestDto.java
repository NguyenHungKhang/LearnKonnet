package com.lms.learnkonnet.dtos.requests.choice;

import com.lms.learnkonnet.models.Question;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChoiceRequestDto {
    private Long questionId;
    private String content;
    private Boolean isCorrect;
    private Integer order;
    private Boolean caseSensitivity;
}
