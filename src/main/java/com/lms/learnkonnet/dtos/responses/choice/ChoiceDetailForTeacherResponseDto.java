package com.lms.learnkonnet.dtos.responses.choice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ChoiceDetailForTeacherResponseDto  {
    private Long id;
    private Long questionId;
    private String content;
    private Boolean isCorrect;
    private Integer order;
    private Boolean caseSensitivity;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
}
