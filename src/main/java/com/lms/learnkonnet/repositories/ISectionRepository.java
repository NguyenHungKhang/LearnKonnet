package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Section;
import com.lms.learnkonnet.models.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ISectionRepository extends JpaRepository<Section, Long> {
    Page<Section> findByCourseIdAndNameContaining(Long courseId, String keyword, Pageable pageable);
    Page<Section> findByTopicIdAndNameContaining(Long topicId, String keyword, Pageable pageable);
    List<Section> findAllByCourseId(Long courseId);
    List<Section> findAllByTopicId(Long sectionId);
}
