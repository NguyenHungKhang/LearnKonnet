package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.Topic;
import com.lms.learnkonnet.models.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ITopicRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findByCourse_IdAndNameContaining(Long courseId, String keyword, Pageable pageable);
    List<Topic> findAllByCourse_Id(Long courseId);
    Page<Topic> findByCourse_IdAndNameContainingAndStatusAndIsDeletedFalse(Long courseId, String keyword, Status status, Pageable pageable);
    @Query("SELECT DISTINCT t FROM Topic t " +
            "LEFT JOIN FETCH t.sections s " +
            "LEFT JOIN FETCH s.materials m " +
            "LEFT JOIN FETCH s.exercises e " +
            "WHERE t.course.id = :courseId " +
            "AND t.status = :topicStatus " +
            "AND t.isDeleted = false " +
            "AND s.status = :sectionStatus " +
            "AND s.isDeleted = false " +
            "AND m.status = :materialStatus " +
            "AND m.isDeleted = false " +
            "AND e.status = :exerciseStatus " +
            "AND e.isDeleted = false")
    Page<Topic> findFullTopicWithStatus(
            @Param("courseId") Long courseId,
            @Param("topicStatus") Status topicStatus,
            @Param("sectionStatus") Status sectionStatus,
            @Param("materialStatus") Status materialStatus,
            @Param("exerciseStatus") Status exerciseStatus,
            Pageable pageable
    );
    List<Topic> findAllByCourse_IdAndStatusAndIsDeletedFalse(Long courseId, Status status);
    @Query("SELECT DISTINCT t FROM Topic t " +
            "LEFT JOIN FETCH t.sections s " +
            "LEFT JOIN FETCH s.materials m " +
            "LEFT JOIN FETCH s.exercises e " +
            "WHERE t.course.id = :courseId " +
            "AND t.status = :topicStatus " +
            "AND t.isDeleted = false " +
            "AND s.status = :sectionStatus " +
            "AND s.isDeleted = false " +
            "AND m.status = :materialStatus " +
            "AND m.isDeleted = false " +
            "AND e.status = :exerciseStatus " +
            "AND e.isDeleted = false")
    List<Topic> findFullTopicWithStatus(
            @Param("courseId") Long courseId,
            @Param("topicStatus") Status topicStatus,
            @Param("sectionStatus") Status sectionStatus,
            @Param("materialStatus") Status materialStatus,
            @Param("exerciseStatus") Status exerciseStatus
    );
    Optional<Topic> findByIdAndStatusAndIsDeletedFalse(Long id, Status status);
}
