package com.lms.learnkonnet.repositories;

import com.lms.learnkonnet.models.AssignmentMaterial;
import com.lms.learnkonnet.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAssignmentMaterialRepository extends JpaRepository<AssignmentMaterial, Long> {
}
