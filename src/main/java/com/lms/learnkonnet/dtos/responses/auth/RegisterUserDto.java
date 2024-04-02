package com.lms.learnkonnet.dtos.responses.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterUserDto {
    private String email;
    private String password;
    private String givenName;
    private String familyName;
    private String birth;
    private String avatar;
    private String confirmPassword;
}
