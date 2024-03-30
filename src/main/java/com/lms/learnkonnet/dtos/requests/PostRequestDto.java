package com.lms.learnkonnet.dtos.requests;

import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.enums.PostType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostRequestDto {
    private Long courseId;
    private String content;
    private String image;
    private PostType postType;
    private Timestamp postAt;
}
