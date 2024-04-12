package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Comment;
import com.lms.learnkonnet.models.Member;
import com.lms.learnkonnet.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPost_Id(Long postId, Pageable pageable);
    List<Comment> findAllByPost_Id(Long postId);
}
