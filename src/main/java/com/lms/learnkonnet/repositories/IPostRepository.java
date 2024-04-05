package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Post;
import com.lms.learnkonnet.models.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IPostRepository extends JpaRepository<Post, Long> {
    Page<Post> findByCourseIdAndNameContaining(Long courseId, String keyword, Pageable pageable);
    List<Post> findAllByCourseId(Long courseId);
}
