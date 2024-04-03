package com.lms.learnkonnet.services;


import com.lms.learnkonnet.dtos.requests.user.CreateUserRequestDto;
import com.lms.learnkonnet.dtos.requests.user.UpdateUserRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.user.UserDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserOwnerResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserSumaryResponseDto;
import jakarta.persistence.criteria.Order;

import java.util.List;

public interface IUserService {
    PageResponse<UserDetailResponseDto> getPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize);
    List<UserDetailResponseDto> getAll();
    UserDetailResponseDto getById(Long id);
    UserOwnerResponseDto getByOwner(Long id);
    UserDetailResponseDto getByEmail(String email);
    UserSumaryResponseDto getSumaryById(Long id);
    UserSumaryResponseDto getSumaryByEmail(Long id);
    UserDetailResponseDto add(CreateUserRequestDto user);
    UserDetailResponseDto update(Long id, UpdateUserRequestDto user);
    Boolean softDelete(Long id);
    Boolean delete(Long id);
}
