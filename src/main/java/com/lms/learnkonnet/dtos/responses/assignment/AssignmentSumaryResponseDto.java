package com.lms.learnkonnet.dtos.responses.assignment;

import com.lms.learnkonnet.models.enums.AssignmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignmentSumaryResponseDto {
    private Long id;
    private Long exerciseId;
    private String name;
    private AssignmentType assignmentType;
    private Boolean isResubmit;
    private Boolean isAcceptImage;
    private Boolean isAcceptText;
    private Boolean isAcceptFile;
}
