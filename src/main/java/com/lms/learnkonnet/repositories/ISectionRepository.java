package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Section;
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
public interface ISectionRepository extends JpaRepository<Section, Long> {
    Page<Section> findByCourseIdAndNameContaining(Long courseId, String keyword, Pageable pageable);
    Page<Section> findByTopic_IdAndNameContaining(Long topicId, String keyword, Pageable pageable);
    @Query("SELECT DISTINCT s FROM Section s " +
            "LEFT JOIN FETCH s.materials ms " +
            "LEFT JOIN FETCH ms.material m " +
            "LEFT JOIN FETCH s.exercises es " +
            "LEFT JOIN FETCH es.exercise e " +
            "WHERE s.topic.id = :topicId " +
            "AND s.topic.status = :topicStatus " +
            "AND s.topic.isDeleted = false " +
            "AND s.course.status = :courseStatus " +
            "AND s.course.isDeleted = false " +
            "AND s.status = :sectionStatus " +
            "AND s.isDeleted = false " +
            "AND m.status = :materialStatus " +
            "AND m.isDeleted = false " +
            "AND e.status = :exerciseStatus " +
            "AND e.isDeleted = false " +
            "AND (LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(s.desc) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Section> findSuperSectionsByTopicIdAndAllStatusAndAllIsDeleted(
            @Param("topicId") Long topicId,
            @Param("topicStatus") Status topicStatus,
            @Param("courseStatus") Status courseStatus,
            @Param("sectionStatus") Status sectionStatus,
            @Param("materialStatus") Status materialStatus,
            @Param("exerciseStatus") Status exerciseStatus,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("SELECT DISTINCT s FROM Section s " +
            "LEFT JOIN FETCH s.materials ms " +
            "LEFT JOIN FETCH ms.material m " +
            "LEFT JOIN FETCH s.exercises es " +
            "LEFT JOIN FETCH es.exercise e " +
            "WHERE s.topic.id = :topicId " +
            "AND s.topic.status = :topicStatus " +
            "AND s.topic.isDeleted = false " +
            "AND s.course.status = :courseStatus " +
            "AND s.course.isDeleted = false " +
            "AND s.status = :sectionStatus " +
            "AND s.isDeleted = false " +
            "AND m.status = :materialStatus " +
            "AND m.isDeleted = false " +
            "AND e.status = :exerciseStatus " +
            "AND e.isDeleted = false " +
            "AND (LOWER(s.name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR LOWER(s.desc) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Section> findSuperSectionsByTopicIdAndAllStatusAndAllIsDeleted(
            @Param("topicId") Long topicId,
            @Param("topicStatus") Status topicStatus,
            @Param("courseStatus") Status courseStatus,
            @Param("sectionStatus") Status sectionStatus,
            @Param("materialStatus") Status materialStatus,
            @Param("exerciseStatus") Status exerciseStatus
    );

    List<Section> findAllByCourseId(Long courseId);
    List<Section> findAllByTopicId(Long sectionId);
    Optional<Section> findByIdAndStatusAndIsDeletedFalse(Long id, Status status);
}
