package com.lms.learnkonnet.dtos.responses.topic;

import com.lms.learnkonnet.dtos.responses.section.SectionDetailResponseDto;
import com.lms.learnkonnet.models.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TopicDetailResponseDto {
    private Long id;
    private String slug;
    private Long courseId;
    private Long order;
    private String name;
    private String desc;
    private Timestamp startedAt;
    private Timestamp endedAt;
    private Status status;
    private Timestamp modifiedAt;
    private Timestamp createdAt;
    private Set<SectionDetailResponseDto> sectionDetailResponseDtos = new HashSet<>();
}
