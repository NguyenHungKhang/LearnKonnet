package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.enums.MemberType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemberRepository extends JpaRepository<Member, Long> {
    Member findByUserIdAndCourseIdAndMemberType(Long userId, Long courseId, MemberType memberType);
}
