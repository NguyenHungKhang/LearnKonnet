package com.lms.learnkonnet.services.impls;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.UUID;

import com.lms.learnkonnet.exceptions.ApiException;
import com.lms.learnkonnet.exceptions.ResourceNotFoundException;
import com.lms.learnkonnet.models.RefreshToken;
import com.lms.learnkonnet.models.User;
import com.lms.learnkonnet.repositories.IRefreshTokenRepository;
import com.lms.learnkonnet.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
public class RefreshTokenService {
	@Autowired
	private IRefreshTokenRepository refreshTokenRepository;

	@Autowired
	private IUserRepository userRepository;

	public Optional<RefreshToken> findByToken(String token) {
		return refreshTokenRepository.findByToken(token);
	}

	public RefreshToken createRefreshToken(Long userId) {
		RefreshToken refreshToken = new RefreshToken();

		refreshToken.setUser(userRepository.findById(userId).get());
		refreshToken.setExpiryDate(new Timestamp(System.currentTimeMillis() + 14L * 24L * 60L * 60L * 1000L));
		refreshToken.setToken(UUID.randomUUID().toString());

		refreshToken = refreshTokenRepository.save(refreshToken);
		return refreshToken;
	}

	public RefreshToken verifyExpiration(RefreshToken token) {
		if (token.getExpiryDate().compareTo(new Timestamp(System.currentTimeMillis())) < 0) {
			refreshTokenRepository.delete(token);
			throw new ApiException("Refresh Token đã hết hạn");
		}

		return token;
	}

	@Transactional
	public int deleteByUserId(Long userId) {
		User existUser = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
		return refreshTokenRepository.deleteByUser(existUser);
	}
}
