package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.Topic;
import com.lms.learnkonnet.models.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITopicRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findByCourse_IdAndNameContaining(Long courseId, String keyword, Pageable pageable);
    List<Topic> findAllByCourse_Id(Long courseId);
    Page<Topic> findByCourse_IdAndNameContainingAndStatusAndIsDeletedFalse(Long courseId, String keyword, Status status, Pageable pageable);
    List<Topic> findAllByCourse_IdAndStatusAndIsDeletedFalse(Long courseId, Status status);
    Optional<Topic> findByIdAndStatusAndIsDeletedFalse(Long id, Status status);
}
