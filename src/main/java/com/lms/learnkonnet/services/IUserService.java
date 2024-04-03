package com.lms.learnkonnet.services;


import com.lms.learnkonnet.dtos.requests.user.CreateUserRequestDto;
import com.lms.learnkonnet.dtos.responses.user.UserDetailResponseDto;

public interface IUserService {
    public UserDetailResponseDto add(CreateUserRequestDto user);
}
