package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemberRepository extends JpaRepository<Member, Long> {
}
