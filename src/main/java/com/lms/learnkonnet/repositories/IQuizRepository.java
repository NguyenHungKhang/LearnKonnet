package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuizRepository extends JpaRepository<Quiz, Long> {
}
