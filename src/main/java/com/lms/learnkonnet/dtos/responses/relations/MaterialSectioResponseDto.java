package com.lms.learnkonnet.dtos.responses.relations;

import com.lms.learnkonnet.dtos.responses.material.MaterialDetailResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaterialSectioResponseDto {
    private Long sectionId;
    private MaterialDetailResponseDto material;
    private Integer order;
}
