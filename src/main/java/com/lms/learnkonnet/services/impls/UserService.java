package com.lms.learnkonnet.services.impls;

import com.lms.learnkonnet.dtos.requests.user.CreateUserRequestDto;
import com.lms.learnkonnet.dtos.requests.user.UpdateUserRequestDto;
import com.lms.learnkonnet.dtos.responses.common.PageResponse;
import com.lms.learnkonnet.dtos.responses.course.CourseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.course.CourseSumaryResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserOwnerResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserSumaryResponseDto;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.User;
import com.lms.learnkonnet.models.enums.Role;
import com.lms.learnkonnet.repositories.IUserRepository;
import com.lms.learnkonnet.services.IUserService;
import com.lms.learnkonnet.utils.ModelMapperUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ModelMapperUtil modelMapperUtil;
    @Override
    public PageResponse<UserDetailResponseDto> getPageableList(String keyword, String sortField, String sortDir, int pageNum, int pageSize) {
        Sort sort = Sort.by(sortDir.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortField);
        if(sortField == null || sortDir == null) sort = Sort.unsorted();
        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);
        Page<User> usersPage = userRepository.findByEmailContainingOrNameContaining(keyword, pageable);
        List<UserDetailResponseDto> usersDtoPage = modelMapperUtil.mapList(usersPage.getContent(), UserDetailResponseDto.class);

        return new PageResponse<>(
                usersDtoPage,
                usersPage.getNumber(),
                usersPage.getSize(),
                usersPage.getTotalElements(),
                usersPage.getTotalPages(),
                usersPage.isLast()
        );
    }

    @Override
    public List<UserDetailResponseDto> getAll() {
        List<User> users = userRepository.findAll();
        return modelMapperUtil.mapList(users, UserDetailResponseDto.class);
    }

    @Override
    public UserDetailResponseDto getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return modelMapperUtil.mapOne(user, UserDetailResponseDto.class);
    }

    @Override
    public UserOwnerResponseDto getByOwner(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return modelMapperUtil.mapOne(user, UserOwnerResponseDto.class);
    }

    @Override
    public UserDetailResponseDto getByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return modelMapperUtil.mapOne(user, UserDetailResponseDto.class);
    }

    @Override
    public UserSumaryResponseDto getSumaryById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return modelMapperUtil.mapOne(user, UserSumaryResponseDto.class);
    }

    @Override
    public UserSumaryResponseDto getSumaryByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return modelMapperUtil.mapOne(user, UserSumaryResponseDto.class);
    }

    @Override
    public Long getIdByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
        return user.getId();
    }

    @Override
    public UserDetailResponseDto add(CreateUserRequestDto user) {
        User newUser = modelMapperUtil.mapOne(user, User.class);
        newUser.setRole(Role.ROLE_USER);
        User savedUser = userRepository.save(newUser);
        return modelMapperUtil.mapOne(savedUser, UserDetailResponseDto.class);
    }

    @Override
    public UserDetailResponseDto update(Long id, UpdateUserRequestDto user, Long currentUserId) {
        User existUser = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        existUser.setFamilyName(user.getFamilyName());
        existUser.setGivenName(user.getGivenName());
        existUser.setBirth(user.getBirth());
        User savedUser = userRepository.save(existUser);
        return modelMapperUtil.mapOne(savedUser, UserDetailResponseDto.class);
    }

    @Override
    public Boolean softDelete(Long id, Long currentUserId) {
        User existUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        User currentUser = userRepository.findById(currentUserId)
                .orElseThrow(() -> new ResourceNotFoundException("Current user", "Id", currentUserId));
        existUser.setIsDeleted(!existUser.getIsDeleted());
        existUser.setUpdatedBy(currentUser);
        User savedUser= userRepository.save(existUser);
        return savedUser.getIsDeleted();
    }

    @Override
    public Boolean delete(Long id, Long currentUserId) {
        User existUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", id));
        userRepository.delete(existUser);
        return true;
    }
}
