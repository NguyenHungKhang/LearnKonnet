package com.lms.learnkonnet.dtos.requests.assignment;

import com.lms.learnkonnet.dtos.requests.assignment.material.AssignmentMaterialRequestDto;
import com.lms.learnkonnet.models.enums.AssignmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

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
    private Set<AssignmentMaterialRequestDto> assignmentMaterialRequestDtos= new HashSet<>();
}
