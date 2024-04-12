package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Material;
import com.lms.learnkonnet.models.Section;
import com.lms.learnkonnet.models.Topic;
import com.lms.learnkonnet.models.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IMaterialRepository extends JpaRepository<Material, Long> {
    Page<Material> findByCourse_IdAndNameContaining(Long courseId, String keyword, Pageable pageable);
//    Page<Material> findAllBySections_Section_IdAndNameContaining(Long sectionId, String name, Pageable pageable);

    @Query("SELECT m FROM Material m " +
            "WHERE m.course.id = :courseId " +
            "AND m.status = :materialStatus " +
            "AND m.isDeleted = false " +
            "AND (LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    Page<Material> findMaterialsByCourseIdAndStatus(
            @Param("courseId") Long courseId,
            @Param("materialStatus") Status materialStatus,
            @Param("keyword") String keyword,
            Pageable pageable
    );

//    @Query("SELECT m FROM Material m " +
//            "WHERE m.course.id = :courseId " +
//            "AND m.status = :materialStatus " +
//            "AND m.isDeleted = false" +
//            "AND (LOWER(m.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
//    Page<Material> findMaterialsBySectiobnIdAndStatus(
//            @Param("courseId") Long courseId,
//            @Param("materialStatus") Status materialStatus,
//            @Param("keyword") String keyword,
//            Pageable pageable
//    );

    List<Material> findAllByCourse_Id(Long courseId);
    List<Material> findAllBySections_Section_Id(Long sectionId);

    @Query("SELECT m FROM Material m " +
            "WHERE m.course.id = :courseId " +
            "AND m.status = :materialStatus " +
            "AND m.isDeleted = false")
    List<Material> findMaterialsByCourseIdAndStatus(
            @Param("courseId") Long courseId,
            @Param("materialStatus") Status materialStatus
    );

    Optional<Material> findByIdAndStatusAndIsDeletedFalse(Long id, Status status);
}
