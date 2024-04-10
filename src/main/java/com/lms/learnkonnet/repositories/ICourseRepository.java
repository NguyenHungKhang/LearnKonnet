package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.User;
import com.lms.learnkonnet.models.enums.MemberStatus;
import com.lms.learnkonnet.models.enums.MemberType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ICourseRepository extends JpaRepository<Course, Long> {
    Page<Course> findByNameContaining(String keyword, Pageable pageable);
    List<Course> findAllByUserId(Long userId);
    Optional<Course> findByCode(String code);
    Page<Course> findByUserIdAndNameContaining(Long userId, String keyword, Pageable pageable);
//    Page<Course> findByMembersUserIdAndMembersStatusAndNameContaining(Long userId, MemberStatus status, String keyword, Pageable pageable);
    Page<Course> findByMembersUserIdAndMembersStatusAndNameContaining(Long userId, MemberStatus status, String keyword, Pageable pageable);
    Page<Course> findByMembersUser_IdAndMembers_StatusAndMembers_TypeAndNameContaining(
            Long userId, MemberStatus status, MemberType memberType, String keyword, Pageable pageable);

}
