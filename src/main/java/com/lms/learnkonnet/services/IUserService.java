package com.lms.learnkonnet.services;


import com.lms.learnkonnet.dtos.requests.user.CreateUserRequestDto;
import com.lms.learnkonnet.dtos.requests.user.UpdateUserRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.user.UserDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserOwnerResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserSumaryResponseDto;
import com.lms.learnkonnet.models.User;
import jakarta.persistence.criteria.Order;

import java.util.List;

public interface IUserService {
    PageResponse<UserDetailResponseDto> getPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize);
    List<UserDetailResponseDto> getAll();
    UserDetailResponseDto getById(Long id);
    UserOwnerResponseDto getByOwner(Long id);
    UserDetailResponseDto getByEmail(String email);
    UserSumaryResponseDto getSumaryById(Long id);
    UserSumaryResponseDto getSumaryByEmail(String email);
    Long getIdByEmail(String email);
    UserDetailResponseDto add(CreateUserRequestDto user);
    UserDetailResponseDto update(Long id, UpdateUserRequestDto user, Long currentUserId);
    Boolean softDelete(Long id, Long currentUserId);
    Boolean delete(Long id, Long currentUserId);
    Long checkUserAvaiableByEmail(String email);
    Long checkUserAvaiableById(Long id);
    User verifyIDToken(String idToken);
    String processOAuthPostLogin(String idToken);
}
