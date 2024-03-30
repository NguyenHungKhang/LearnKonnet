package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAssignmentRepository extends JpaRepository<Assignment, Long> {
}
