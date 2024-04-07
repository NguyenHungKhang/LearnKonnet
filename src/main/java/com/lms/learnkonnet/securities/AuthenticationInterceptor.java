package com.lms.learnkonnet.securities;

import java.security.Principal;

import com.lms.learnkonnet.exceptions.CustomAuthenticationException;
import org.springframework.web.servlet.HandlerInterceptor;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		boolean isAuthenticated = checkAuthentication(request);

		if (isAuthenticated) {
			return true;
		} else {
			throw new CustomAuthenticationException(null);
		}
	}

	private boolean checkAuthentication(HttpServletRequest request) {
		Principal principal = request.getUserPrincipal();

		if (principal != null) {
			return true;
		}

		return false;
	}
}
