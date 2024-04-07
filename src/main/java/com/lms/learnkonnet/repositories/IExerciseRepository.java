package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.dtos.responses.excercise.PasswordAccessExerciseResponseDto;
import com.lms.learnkonnet.models.Exercise;
import com.lms.learnkonnet.models.Material;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IExerciseRepository extends JpaRepository<Exercise, Long> {
    Page<Exercise> findByCourseIdAndNameContaining(Long courseId, String keyword, Pageable pageable);
    Page<Exercise> findAllByExerciseSections_SectionIdAndNameContaining(Long sectionId, String name, Pageable pageable);
    List<Exercise> findAllByCourseId(Long courseId);
    List<Exercise> findAllByExerciseSections_SectionId(Long sectionId);
    @Query("SELECT new com.example.dto.PasswordAccessExerciseResponseDto(e.password) FROM Exercise e WHERE e.id = :id")
    PasswordAccessExerciseResponseDto getPasswordExercise(@Param("id") Long id);

    @Query("SELECT e.password FROM Exercise e WHERE e.id = :id")
    String findPasswordById(@Param("id") Long id);
}
