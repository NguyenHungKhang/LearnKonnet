package com.lms.learnkonnet.repositories.relations;

import com.lms.learnkonnet.models.relations.MemberExercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemberExerciseRepository extends JpaRepository<MemberExercise, Long> {
}

