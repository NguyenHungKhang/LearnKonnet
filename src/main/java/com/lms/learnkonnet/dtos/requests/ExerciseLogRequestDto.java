package com.lms.learnkonnet.dtos.requests;

import com.lms.learnkonnet.models.Exercise;
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
public class ExerciseLogRequestDto {
    private Long exerciseId;
    private String content;
}
