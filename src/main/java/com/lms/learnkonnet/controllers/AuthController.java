package com.lms.learnkonnet.controllers;


import javax.validation.Valid;

import com.lms.learnkonnet.dtos.requests.auth.RefreshTokenRequestDto;
import com.lms.learnkonnet.dtos.responses.user.UserDetailResponseDto;
import com.lms.learnkonnet.exceptions.ApiException;
import com.lms.learnkonnet.exceptions.ApiResponse;
import com.lms.learnkonnet.models.RefreshToken;
import com.lms.learnkonnet.models.User;
import com.lms.learnkonnet.repositories.IUserRepository;
import com.lms.learnkonnet.securities.JwtToken;
import com.lms.learnkonnet.services.IUserService;
import com.lms.learnkonnet.services.impls.RefreshTokenService;
import com.lms.learnkonnet.services.impls.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    IUserService userService = new UserService();
    @Autowired
    IUserRepository userRepository;
    @Autowired
    RefreshTokenService refreshTokenService = new RefreshTokenService();

    @Autowired
    JwtToken jwtToken = new JwtToken();

    @GetMapping("/login/mock/{email}")
    public ResponseEntity<?> skipAuthenticate(@PathVariable String email, HttpServletResponse response) {
        User user = userRepository.findByEmail(email).get();
        String authToken = jwtToken.generateToken(user);
        final ResponseCookie accessTokenCookie = ResponseCookie.from("AUTH-TOKEN", authToken).httpOnly(false).maxAge(3600)
                .path("/").secure(false).build();

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user.getId());
        String refreshTokenString = refreshToken.getToken();
        final ResponseCookie refreshTokenCookie = ResponseCookie.from("REFRESH-TOKEN", refreshTokenString)
                .httpOnly(false).maxAge(14 * 24 * 3600).path("/").secure(false).build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        return new ResponseEntity<String>(authToken, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity LoginWithGoogleOauth2(@RequestBody String idToken, HttpServletResponse response) {
        idToken = idToken.replaceAll("\"", "");
        String authToken = userService.processOAuthPostLogin(idToken);
        final ResponseCookie accessTokenCookie = ResponseCookie.from("AUTH-TOKEN", authToken).httpOnly(false).maxAge(3600)
                .path("/").secure(false).build();

        Long userId = userService.getIdByEmail(userService.verifyIDToken(idToken).getEmail());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userId);
        String refreshTokenString = refreshToken.getToken();
        final ResponseCookie refreshTokenCookie = ResponseCookie.from("REFRESH-TOKEN", refreshTokenString)
                .httpOnly(false).maxAge(14 * 24 * 3600).path("/").secure(false).build();

        response.addHeader(HttpHeaders.SET_COOKIE, accessTokenCookie.toString());
        response.addHeader(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity refreshtoken(@Valid @RequestBody RefreshTokenRequestDto token, HttpServletResponse response) {
        String requestRefreshToken = token.getToken();

        return refreshTokenService.findByToken(requestRefreshToken).map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser).map(user -> {
                    String newToken = jwtToken.generateToken(user);
                    Cookie cookie = new Cookie("AUTH-TOKEN", newToken);
                    cookie.setHttpOnly(false);
                    cookie.setMaxAge(3600);
                    cookie.setPath("/");
                    cookie.setSecure(false);
                    response.addCookie(cookie);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ApiException("Renew token failed"));

    }

    @GetMapping("/user-info")
    public ResponseEntity<?> getUserInfo(Principal principal) {
        UserDetailResponseDto user = userService.getByEmail(principal.getName());
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(Principal principal) {
        Long userId = userService.getIdByEmail(principal.getName());
        refreshTokenService.deleteByUserId(userId);
        return new ResponseEntity<ApiResponse>(new ApiResponse("Logout successful", true), HttpStatus.OK);
    }
}
