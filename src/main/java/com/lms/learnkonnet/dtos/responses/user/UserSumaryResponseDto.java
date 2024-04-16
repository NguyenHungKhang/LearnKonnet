package com.lms.learnkonnet.dtos.responses.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSumaryResponseDto {
    private Long id;
    private String slug;
    private String email;
    private String givenName;
    private String familyName;
    private String code;
    private String avatar;
}
