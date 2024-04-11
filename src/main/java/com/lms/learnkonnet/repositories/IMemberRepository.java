package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Topic;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findByTypeAndCourse_IdAndUser_FullNameContainingOrUser_EmailContaining(
            MemberType type, Long courseId, String keyword, Pageable pageable);
    Page<Member> findByCourse_IdAndUser_FullNameContainingOrUser_EmailContaining(
            Long courseId, String keyword, Pageable pageable);
    Page<Member> findByUserEmailContainingIgnoreCaseOrUserFullNameContainingIgnoreCaseAndTypeAndStatusAndCourseId(
            String emailKeyword, String fullNameKeyword,
            MemberType memberType, MemberStatus memberStatus, Long courseId,
            Pageable pageable);
    Optional<Member> findByUserIdAndCourseId(Long userId, Long courseId);
    List<Member> findAllByCourseId(Long courseId);
    List<Member> findByTypeAndCourseId(MemberType type, Long courseId);
    boolean existsByUser_IdAndCourse_IdOrCourse_User_Id(Long userId, Long courseId, Long courseOwnerId);
}