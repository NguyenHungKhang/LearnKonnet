package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IRefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
