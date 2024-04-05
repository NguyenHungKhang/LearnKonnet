package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Course;
import com.lms.learnkonnet.models.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITopicRepository extends JpaRepository<Topic, Long> {
    Page<Topic> findByCourseIdAndNameContaining(Long courseId, String keyword, Pageable pageable);
    List<Topic> findAllByCourseId(Long courseId);
}
