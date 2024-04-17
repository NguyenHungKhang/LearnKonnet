package com.lms.learnkonnet.securities;

import java.io.BufferedReader;
import java.security.Principal;

import com.lms.learnkonnet.exceptions.CustomAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.util.ContentCachingRequestWrapper;

public class AuthenticationInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// Kiểm tra xem principal có tồn tại hay đã đăng nhập không
		if (request.getUserPrincipal() != null) {
			// Nếu có, cho phép request đi tiếp
			return true;
		} else {
			// Nếu không, trả về lỗi 401 (Unauthorized)
			response.sendError(HttpStatus.UNAUTHORIZED.value(), "Unauthorized");
			return false;
		}
	}
}
