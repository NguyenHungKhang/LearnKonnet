package com.lms.learnkonnet.controllers;

import com.lms.learnkonnet.dtos.requests.course.CourseRequestDto;
import com.lms.learnkonnet.dtos.requests.user.CreateUserRequestDto;
import com.lms.learnkonnet.dtos.requests.user.UpdateUserRequestDto;
import com.lms.learnkonnet.dtos.responses.course.CourseDetailResponseDto;
import com.lms.learnkonnet.dtos.responses.user.UserDetailResponseDto;
import com.lms.learnkonnet.repositories.IUserRepository;
import com.lms.learnkonnet.services.IUserService;
import com.lms.learnkonnet.services.impls.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private IUserService userService = new UserService();
    @Autowired
    private IUserRepository userRepository;
    // add
    @PostMapping("/")
    public ResponseEntity<?> add(@RequestBody CreateUserRequestDto user) {
        UserDetailResponseDto newUser = userService.add(user);
        return new ResponseEntity<UserDetailResponseDto>(newUser, HttpStatus.CREATED);
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody UpdateUserRequestDto user, @PathVariable Long id, Principal principal) {
        Long currentUserId = userService.getIdByEmail(principal.getName());
        UserDetailResponseDto updatedUser = userService.update(id, user, currentUserId);
        return new ResponseEntity<UserDetailResponseDto>(updatedUser, HttpStatus.OK);
    }
    // delete

    // softDeleted

}
