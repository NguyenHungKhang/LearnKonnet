package com.lms.learnkonnet.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignmentMaterialRequestDto {
    private Long assignmentId;
    private Long fileId;
}
