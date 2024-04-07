package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Comment;
import com.lms.learnkonnet.models.Question;
import com.lms.learnkonnet.models.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IQuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findByQuizIdAndContentContaining(Long quizId, String keyword, Pageable pageable);
}
