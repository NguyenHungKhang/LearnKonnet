package com.lms.learnkonnet.dtos.responses.user;

import java.sql.Timestamp;

public class UserDetailResponseDto {
    private Long id;
    private String slug;
    private String email;
    private String givenName;
    private String familyName;
    private String birth;
    private String code;
    private String avatar;
    private Timestamp createdAt;
}
