package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Choice;
import com.lms.learnkonnet.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IChoiceRepository extends JpaRepository<Choice, Long> {
}
