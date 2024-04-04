package com.lms.learnkonnet.dtos.requests.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UpdateUserRequestDto {
    private String givenName;
    private String familyName;
    private Timestamp birth;
    private String avatar;
}
