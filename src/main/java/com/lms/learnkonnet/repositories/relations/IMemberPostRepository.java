package com.lms.learnkonnet.repositories.relations;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemberPostRepository extends JpaRepository<MemberPost, Long> {
}
