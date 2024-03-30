package com.lms.learnkonnet.repositories.relations;

import com.lms.learnkonnet.models.relations.ExerciseSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExerciseSectionRepository extends JpaRepository<ExerciseSection, Long> {
}
