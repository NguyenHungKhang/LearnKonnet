package com.lms.learnkonnet.dtos.responses.assignment.material;

import com.lms.learnkonnet.dtos.responses.file.FileResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AssignmentMaterialResponseDto {
    private Long id;
    private Long assignmentId;
    private FileResponseDto file;
}
