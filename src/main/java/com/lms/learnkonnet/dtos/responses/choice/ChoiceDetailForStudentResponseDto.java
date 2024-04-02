package com.lms.learnkonnet.dtos.responses.choice;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChoiceDetailForStudentResponseDto {
    private Long id;
    private Long questionId;
    private String content;
    private Integer order;
    private Boolean caseSensitivity;
}
