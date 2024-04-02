package com.lms.learnkonnet.dtos.responses.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileResponseDto {
    private Long id;
    private String name;
    private String type;
    private String url;

}
