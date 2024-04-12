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
            MemberType type, Long courseId, String keyword1, String keyword2, Pageable pageable);
    Page<Member> findByCourse_IdAndUser_FullNameContainingOrUser_EmailContaining(
            Long courseId, String keyword1, String keyword2, Pageable pageable);
    Page<Member> findByUserEmailContainingIgnoreCaseOrUserFullNameContainingIgnoreCaseAndTypeAndStatusAndCourse_Id(
            String emailKeyword, String fullNameKeyword,
            MemberType memberType, MemberStatus memberStatus, Long courseId,
            Pageable pageable);
    Optional<Member> findByUser_IdAndCourse_Id(Long userId, Long courseId);
    List<Member> findAllByCourse_Id(Long courseId);
    List<Member> findByTypeAndStatusAndCourse_Id(MemberType type, MemberStatus status, Long courseId);
    boolean existsByUser_IdAndCourse_IdOrCourse_User_Id(Long userId, Long courseId, Long courseOwnerId);
}