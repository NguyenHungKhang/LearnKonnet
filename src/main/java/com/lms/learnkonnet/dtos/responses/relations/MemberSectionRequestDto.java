package com.lms.learnkonnet.dtos.responses.relations;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberSectionRequestDto {
    private Long sectionId;
    private Long memberId;
    private String status;
    private String note;
}
