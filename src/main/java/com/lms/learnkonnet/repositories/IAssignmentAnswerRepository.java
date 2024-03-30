package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Answer;
import com.lms.learnkonnet.models.AssignmentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAssignmentAnswerRepository extends JpaRepository<AssignmentAnswer, Long> {
}
