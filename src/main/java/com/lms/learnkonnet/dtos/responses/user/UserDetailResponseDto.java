package com.lms.learnkonnet.dtos.responses.user;

import java.sql.Timestamp;

public class UserDetailResponseDto {
    Long id;
    String slug;
    String email;
    String givenName;
    String familyName;
    String birth;
    String code;
    String avatar;
    Timestamp createdAt;
}
