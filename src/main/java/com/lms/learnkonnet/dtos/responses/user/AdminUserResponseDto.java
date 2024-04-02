package com.lms.learnkonnet.dtos.responses.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminUserResponseDto {
    private Long id;
    private String slug;
    private String email;

    private String givenName;
    private String familyName;
    private String birth;
    private String code;
    private String avatar;
    private Boolean isDeleted;
    private Boolean isActived;
    private Timestamp modifiedAt;
    private Timestamp createdAt;
}
