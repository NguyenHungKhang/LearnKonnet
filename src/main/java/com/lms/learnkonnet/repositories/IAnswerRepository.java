package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAnswerRepository extends JpaRepository<Answer, Long> {
}
