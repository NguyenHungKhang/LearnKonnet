package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.AssignmentMaterial;
import com.lms.learnkonnet.models.Question;
import com.lms.learnkonnet.models.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAssignmentMaterialRepository extends JpaRepository<AssignmentMaterial, Long> {
    Page<AssignmentMaterial> findByAssignment_Id(Long courseId, String keyword, Pageable pageable);
}
