package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Topic;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMemberRepository extends JpaRepository<Member, Long> {
    Page<Member> findByTypeAndCourse_IdAndUser_FullNameContainingOrUser_EmailContaining(
            MemberType type, Long courseId, String keyword1, String keyword2, Pageable pageable);

    Page<Member> findByCourse_IdAndUser_FullNameContainingOrUser_EmailContaining(
            Long courseId, String keyword1, String keyword2, Pageable pageable);

    @Query("SELECT m FROM Member m " +
            "WHERE m.course.id = :courseId " +
            "AND m.status = :memberStatus " +
            "AND m.type = :memberType " +
            "AND (LOWER(m.user.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(m.user.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) ")
    Page<Member> findPageableListMember(
            @Param("courseId") Long courseId,
            @Param("memberStatus") MemberStatus memberStatus,
            @Param("memberType") MemberType memberType,
            @Param("keyword") String keyword,
            Pageable pageable);

    Optional<Member> findByUser_IdAndCourse_Id(Long userId, Long courseId);

    List<Member> findAllByCourse_Id(Long courseId);

    List<Member> findByTypeAndStatusAndCourse_Id(MemberType type, MemberStatus status, Long courseId);

    boolean existsByUser_IdAndCourse_IdOrCourse_User_Id(Long userId, Long courseId, Long courseOwnerId);
}