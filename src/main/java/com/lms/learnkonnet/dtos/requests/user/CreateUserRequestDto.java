package com.lms.learnkonnet.dtos.requests.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreateUserRequestDto {
    private String email;
    private String givenName;
    private String familyName;
    private String birth;
    private String avatar;

}
