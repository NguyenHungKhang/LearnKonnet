package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Choice;
import com.lms.learnkonnet.models.Question;
import com.lms.learnkonnet.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IChoiceRepository extends JpaRepository<Choice, Long> {
    List<Choice> findAllByQuestionId(Long questionId);

}
