package com.lms.learnkonnet.services;


import com.lms.learnkonnet.dtos.requests.user.CreateUserRequestDto;
import com.lms.learnkonnet.dtos.requests.user.UpdateUserRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.user.UserDetailResponseDto;
import jakarta.persistence.criteria.Order;

import java.util.List;

public interface IUserService {
    PageResponse<UserDetailResponseDto> getList(List<Order> orders, int page, int size);
    UserDetailResponseDto getById(Long id);
    UserDetailResponseDto getByEmail(String email);
    UserDetailResponseDto add(CreateUserRequestDto user);
    UserDetailResponseDto update(Long id, UpdateUserRequestDto user);
    Boolean softDelete(Long id);
    Boolean delete(Long id);
}
