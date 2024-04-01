package com.lms.learnkonnet.dtos.requests.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RefreshTokenRequestDto {
    private String token;
}
