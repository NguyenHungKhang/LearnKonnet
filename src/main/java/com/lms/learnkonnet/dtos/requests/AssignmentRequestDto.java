package com.lms.learnkonnet.dtos.requests;

import com.lms.learnkonnet.models.Exercise;
import com.lms.learnkonnet.models.enums.AssignmentType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignmentRequestDto {
    private Long exerciseId;
    private String name;
    private AssignmentType assignmentType;
    private Boolean isResubmit = true;
    private Boolean isAcceptImage = false;
    private Boolean isAcceptText = true;
    private Boolean isAcceptFile = false;
}
