package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.ExerciseLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IExerciseLogRepository extends JpaRepository<ExerciseLog, Long> {
}
