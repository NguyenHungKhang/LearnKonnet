package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Answer;
import com.lms.learnkonnet.models.QuizAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizAnswerRepository extends JpaRepository<QuizAnswer, Long> {
}
