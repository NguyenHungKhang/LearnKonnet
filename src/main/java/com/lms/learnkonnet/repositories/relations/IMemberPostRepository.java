package com.lms.learnkonnet.repositories.relations;

import com.lms.learnkonnet.models.relations.MemberPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemberPostRepository extends JpaRepository<MemberPost, Long> {
}
