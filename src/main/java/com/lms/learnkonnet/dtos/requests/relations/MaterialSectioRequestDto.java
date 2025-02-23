package com.lms.learnkonnet.dtos.requests.relations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MaterialSectioRequestDto {
    private Long sectionId;
    private Long materialId;
    private Integer order;
}
