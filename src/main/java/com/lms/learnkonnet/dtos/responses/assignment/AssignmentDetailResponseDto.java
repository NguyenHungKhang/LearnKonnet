package com.lms.learnkonnet.dtos.responses.assignment;

import com.lms.learnkonnet.dtos.responses.assignment.material.AssignmentMaterialResponseDto;
import com.lms.learnkonnet.models.enums.AssignmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignmentDetailResponseDto {
    private Long id;
    private Long exerciseId;
    private String name;
    private AssignmentType assignmentType;
    private Boolean isResubmit;
    private Boolean isAcceptImage;
    private Boolean isAcceptText;
    private Boolean isAcceptFile;
    private Timestamp createdAt;
    private Timestamp modifiedAt;
    private Set<AssignmentMaterialResponseDto> assignmentMaterialRequestDtos= new HashSet<>();
}
