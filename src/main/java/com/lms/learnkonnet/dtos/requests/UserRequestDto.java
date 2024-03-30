package com.lms.learnkonnet.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRequestDto {
    private String givenName;
    private String familyName;
    private String birth;
    private String avatar;

}
