package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.MemberAttempt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemberAttemptRepository extends JpaRepository<MemberAttempt, Long> {
}

