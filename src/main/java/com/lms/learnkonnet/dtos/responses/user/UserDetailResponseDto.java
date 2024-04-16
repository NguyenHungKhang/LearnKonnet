package com.lms.learnkonnet.dtos.responses.user;

import com.lms.learnkonnet.models.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@NoArgsConstructor
@AllArgsConstructor
@Data
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
