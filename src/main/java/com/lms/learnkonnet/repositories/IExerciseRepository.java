package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.dtos.responses.excercise.PasswordAccessExerciseResponseDto;
import com.lms.learnkonnet.models.Exercise;
import com.lms.learnkonnet.models.Material;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Topic;
import com.lms.learnkonnet.models.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IExerciseRepository extends JpaRepository<Exercise, Long> {
    Page<Exercise> findByCourse_IdAndNameContaining(Long courseId, String keyword, Pageable pageable);
    Page<Exercise> findAllBySections_Section_IdAndNameContaining(Long sectionId, String name, Pageable pageable);
    List<Exercise> findAllByCourse_Id(Long courseId);
//    List<Exercise> findAllByExerciseSections_SectionId(Long sectionId);
//    @Query("SELECT new com.lms.learnkonnet.dtos.responses.excercise.PasswordAccessExerciseResponseDto FROM Exercise e WHERE e.id = :id")
//    PasswordAccessExerciseResponseDto getPasswordExercise(@Param("id") Long id);

    @Query("SELECT e.password FROM Exercise e WHERE e.id = :id")
    String findPasswordById(@Param("id") Long id);

    @Query("SELECT e FROM Exercise e " +
            "WHERE e.course.id = :courseId " +
            "AND e.status = :exerciseStatus " +
            "AND e.isDeleted = false " +
            "AND (LOWER(e.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) ")
    Page<Exercise> findExercisesByCourseIdAndStatus(
            @Param("courseId") Long courseId,
            @Param("exerciseStatus") Status exerciseStatus,
            @Param("keyword") String keyword,
            Pageable pageable
    );

    @Query("SELECT e FROM Exercise e " +
            "WHERE e.course.id = :courseId " +
            "AND e.status = :exerciseStatus " +
            "AND e.isDeleted = false")
    List<Exercise> findExercisesByCourseIdAndStatus(
            @Param("courseId") Long courseId,
            @Param("exerciseStatus") Status exerciseStatus
    );

    Optional<Exercise> findByIdAndStatusAndIsDeletedFalse(Long id, Status status);
}
