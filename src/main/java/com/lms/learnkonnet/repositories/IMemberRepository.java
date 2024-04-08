package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Topic;
import com.lms.learnkonnet.models.enums.MemberType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findByTypeAndCourseIdAndUserFamilyNameContainingOrUserGivenNameContainingOrUserEmailContaining(
            MemberType type, Long courseId, String keyword, Pageable pageable);
    Page<Member> findByCourseIdAndUserFamilyNameContainingOrUserGivenNameContainingOrUserEmailContaining(
            Long courseId, String keyword, Pageable pageable);
    Member findByUserIdAndCourseIdAndMemberType(Long userId, Long courseId, MemberType memberType);
    Optional<Member> findByUserIdAndCourseId(Long userId, Long courseId);
    List<Member> findAllByCourseId(Long courseId);
    List<Member> findByTypeAndCourseId(MemberType type, Long courseId);
}
