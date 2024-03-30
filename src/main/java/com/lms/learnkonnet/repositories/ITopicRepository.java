package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITopicRepository extends JpaRepository<Topic, Long> {
}
